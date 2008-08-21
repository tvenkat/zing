/*
 * Copyright 2008 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iLabs.spice.handler;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.myfaces.trinidad.model.UploadedFile;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.Image;
import com.iLabs.spice.dto.UserAlbum;
import com.iLabs.spice.services.IPerson;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * This class is used to handle functionality related to album
 * like upload image,retrieve user album from services, process image 
 * for preview and delete image.
 * @author iLabs
 * @date 01/08/2008
 */
public class ImageUploadHandler extends PageCodeBase {
	
	// myPhoto for upload image.
	private UploadedFile myPhoto;
	// image caption for upload image.
	private String imageCaption;
	// image caption for upload image.
	private String imageId = "";
	//this attribute handle information for the rendering of the component.
	private boolean uploadRendered = true;
	//this attribute handle information for the delete action of the component.
	private String deleteAction = "true";
    
	/**
	 * This function determines whether user can upload image or not.
	 * If the current profile being viewed is owner's profile then user can upload image.
	 * 
	 * @return boolean which determines whether to render upload html component or not. 
	 */
	
	public boolean getUploadRendered() {
		ProfileBean currenProfile = (ProfileBean)getSessionScope().get("currentProfile");
		ProfileBean ownerProfile = (ProfileBean)getSessionScope().get("ownerProfile");
		if(ownerProfile.getUserAuth().getUserId()!=currenProfile.getUserAuth().getUserId())
			uploadRendered = false;
		return uploadRendered;
	}
    
    /**
	 * Setter for uploadRender.
	 * @param uploadRendered boolean
	 */
	public void setUploadRendered(boolean uploadRendered) {
		this.uploadRendered = uploadRendered;
	}

	/**
	 * Getter for myPhoto.
	 * @return UploadedFile
	 */
	public UploadedFile getMyPhoto() {
		return myPhoto;
	}
   
    /**
	 * Setter for myPhoto.
	 * @param myPhoto UploadedFile
	 */
	public void setMyPhoto(UploadedFile myPhoto) {
		this.myPhoto = myPhoto;
	}
   
    // List of all the user AlbumList available in the system.
	private Collection userAlbumList;
    /**
	 * This function is used to fetch user album.
	 * It uses getPersonAlbum service to receive user images. 
	 * 
	 * @return Collection which contains list of user images.
	 */
	public Collection getUserAlbumList() {

		IPerson person;
		try {
			
			
			ProfileBean profileBean = (ProfileBean)getSessionScope().get("currentProfile");
			int userId = profileBean.getUserAuth().getUserId();
			
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			List<UserAlbum> userAlbumList = (List<UserAlbum>) person
					.getPersonAlbum(userId);
			getSessionScope().put("userAlbumList", userAlbumList);

			return userAlbumList.get(0).getImage();
		} catch (SysException e) {
		  e.printStackTrace();
		}

		return null;
	}

	/**
	 * Setter for userAlbumList.
	 * @param userAlbumList Collection
	 */
	public void setUserAlbumList(Collection userAlbumList) {
		this.userAlbumList = userAlbumList;
	}
    /**
	 * This function is used to upload image.
	 * It creates a user directory for every user and resizes the image using saveCropedFile() function.
	 * It then saves the image using addToAlbum service.
	 * 
	 * @return String
	 */
	public String processMyPhoto() {
		boolean added = false;
		try {
			//It checks whether the image provided by user is null or not
			if(getMyPhoto() != null && getMyPhoto().getInputStream() != null){
			InputStream inputStream = getMyPhoto().getInputStream();

			String fileName = getMyPhoto().getFilename();
			ProfileBean profileBean = (ProfileBean)getSessionScope().get("currentProfile");
			int userId = profileBean.getUserAuth().getUserId();
			String userdir = "" + userId;
			String basePath=getFacesContext().getExternalContext().getResource("/userdata").toString();
			basePath=basePath.replaceAll("file:/", "");
			String filepath = basePath+"/"+ userdir;
			boolean success = (new File(filepath)).mkdir();
			long time = System.currentTimeMillis();
			time = time % 10000;
			String userFileName = userdir + time + fileName;
			String finalImagePath = filepath +"/"+ userFileName;
			saveCropedFile(inputStream, finalImagePath);
			
			String imageUrl = "/userdata/"+userdir+ "/"+ userFileName;

		    IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
			List<Image> imageList = new ArrayList<Image>();

			Image image = (Image) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestMap().get("image");
			String caption = image.getImageCaption();
			image.setImageCaption(caption);
			image.setImageName(imageUrl);
			image.setThumbName("");
			image.setUserId(userId);
			imageList.add(image);
			UserAlbum userAlbum = new UserAlbum();
			userAlbum.getImage().addAll(imageList);
			added = person.addToAlbum(userAlbum);
			getUserAlbumList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (added){
			return "success";
		}
		else{
			getFacesContext().addMessage("frmMyAlbum:button", new FacesMessage("Error processing image!!"));
			return "failure";
		}

	}
	
	/**
	 * This function is used to crop,encode the image provided by user before saving it to the file system.
	 * @param inputStream InputStream
	 * @param file String
	 */
	private void saveCropedFile(InputStream inputStream, String file){
		BufferedImage image = cropImage(inputStream);
		encodeImage(image, file);
	}
	/**
	 * It scale the image for a particular size.
	 * @param inputStream  InputStream
	 * @return BufferedImage
	 */
	private BufferedImage cropImage(InputStream inputStream){
		BufferedImage image = null;
		try {
			image = ImageIO.read(inputStream);
			image = scaleToSize(600, 450, image);
		} catch (IOException e) {
		e.printStackTrace();
		}
		return image;
	}
   
   /**
	 * This method encode the image.
	 * @param bi BufferedImage
	 * @param str String
	 */
	private void encodeImage(BufferedImage bi, String str) {
		if (bi != null && str != null) {

			// save image as Jpeg
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(str);
			} catch (java.io.FileNotFoundException fnf) {
				System.out.println("File Not Found");
			}

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			param.setQuality(0.75f, false);

			try {
				encoder.encode(bi);
				out.close();
			} catch (java.io.IOException io) {
				System.out.println(io);
			}
		}
	}

	/**
	 * This method is use to scale the size of image. 
	 * @param nMaxWidth int
	 * @param nMaxHeight int
	 * @param imgSrc BufferedImage
	 * @return BufferedImage
	 */
	private BufferedImage scaleToSize(int nMaxWidth, int nMaxHeight,
			BufferedImage imgSrc) {
		int nHeight = imgSrc.getHeight();
		int nWidth = imgSrc.getWidth();
		double scaleX = (double) nMaxWidth / (double) nWidth;
		double scaleY = (double) nMaxHeight / (double) nHeight;
		double fScale = Math.min(scaleX, scaleY);
		return scale(fScale, imgSrc);
	}

	/**
	 * This method used for transformation of the image.
	 * @param scale double
	 * @param srcImg BufferedImage
	 * @return BufferedImage
	 */
	private BufferedImage scale(double scale, BufferedImage srcImg) {
		if (scale == 1) {
			return srcImg;
		}
		AffineTransformOp op = new AffineTransformOp(AffineTransform
				.getScaleInstance(scale, scale), null);
		return op.filter(srcImg, null);
	}

   /**
	 * Getter for image caption.
	 * @return String
	 */
	public String getImageCaption() {
		return imageCaption;
	}
    
    /**
	 * Setter for image caption.
	 * @param imageCaption String
	 */
	public void setImageCaption(String imageCaption) {
		this.imageCaption = imageCaption;
	}

	/**
	 * This function is used to delete album using deleteAlbum service.
	 * 
	 * @return String
	 */
	public String deleteAlbum() {
		
		try {
			IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			ProfileBean profileBean = (ProfileBean)getSessionScope().get("currentProfile");
			int userId = profileBean.getUserAuth().getUserId();

			String imageId = (String)getRequestParam().get("imageId");
			
			person.deleteAlbum(Integer.parseInt(imageId));
		} catch (SysException e) {
			e.printStackTrace();
		}

		return "success";
	}

	/**
	 * Getter for imageId.
	 * @return String
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * Setter for imageId.
	 * @param imageId String
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * This function is used whether delete component is visible or not.
	 * 
	 * @return String
	 */
	public String getDeleteAction() {
		ProfileBean currenProfile = (ProfileBean)getSessionScope().get("currentProfile");
		ProfileBean ownerProfile = (ProfileBean)getSessionScope().get("ownerProfile");
		//It checks if current profile is not the owner profile then delete action is not display.
		if(ownerProfile.getUserAuth().getUserId()!=currenProfile.getUserAuth().getUserId())
			deleteAction = "false";		
		return deleteAction;
	}

	/**
	 * Setter for delete Action.
	 * @param deleteAction String
	 */
	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}
	
}