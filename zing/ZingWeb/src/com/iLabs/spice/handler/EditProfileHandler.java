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
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.apache.myfaces.trinidad.model.UploadedFile;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.CountriesBean;
import com.iLabs.spice.beans.EditProfileBean;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.services.IPerson;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * This class handle all action related profile updation.
 * 
 * @author iLabs
 * @date 01/08/2008
 */

public class EditProfileHandler extends PageCodeBase {

	// myPhoto for upload image.
	private UploadedFile myPhoto;
	
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

	/**
	 * This function is called when user submits the data to update profile.
	 * It first authenticates the user and after successful authentication, user info is 
	 * set into currentProfile bean and ownerProfile bean.
	 * After successful updation user is redirected to home page.
	 * 
	 * @return String 
	 */
	public String editProfileAction() {

		ProfileBean profileBean = (ProfileBean) getSessionScope().get(
				"currentProfile");
		UserAuth userAuth = profileBean.getUserAuth();

		EditProfileBean editProfileBean = (EditProfileBean) getSessionScope()
				.get("editProfileBean");
		String result = "failure";
		String finalInterest = "";

		/* 
		 * This loop is to convert the list of interests from EditProfileBean
		 * into a single concatenated String 
		 * The String is then set into UserProfile data Object
		 */
		
		for (String interest : editProfileBean.getInterestsList()) {
			if (finalInterest != "")
				finalInterest = finalInterest + "," + interest;
			else
				finalInterest = interest;
		}

		/*
		 * Code for adding new image to user
		 */
		InputStream inputStream = null;
		try {
			if (getMyPhoto() != null && getMyPhoto().getInputStream() != null) {

				inputStream = getMyPhoto().getInputStream();

				String fileName = getMyPhoto().getFilename();
				System.out.println("File Name: " + fileName);
				int fileLength = (int) getMyPhoto().getLength();
				System.out.println("File Length: " + fileLength);

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

				String imageUrl = "/userdata/"+ userdir + "/" + userFileName;

				userAuth.getProfile().setUserImage(imageUrl);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		userAuth.getProfile().setInterests(finalInterest);

		IPerson person;
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			person.updateProfile(userAuth.getProfile());
			result = "success";
		} catch (SysException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This function is called when user wants to update profile
	 * and click on edit profile link on navigation bar.
	 * It brings list of user interests and countries,
	 * then renders the user to registration page.
	 * 
	 * @return String 
	 */
	
	public String preEditAction() {

	   /*
		* This code is to bring list of countries from property file,
		* to show them in dropdown box on editProfile page
		*/


		CountriesBean countriesBean = new CountriesBean();
		ResourceBundle labels = ResourceBundle.getBundle(
				"com/iLabs/spice/nls/Countries", Locale.ENGLISH);
		Enumeration bundleKeys = labels.getKeys();
		List<String> list = Collections.list(bundleKeys);
		Collections.sort(list);
		LinkedHashMap countryList = new LinkedHashMap();

		for (String key : list) {
			String value = labels.getString(key);
			countryList.put(key, value);
		}
		countriesBean.setCountries(countryList);
		getSessionScope().put("countriesBean", countriesBean);

		/*
		* This code is to convert the interests string into a list of interests,
		* to check the checkboxes on editProfile page
		*/	

		ProfileBean profileBean = (ProfileBean) getSessionScope().get(
				"currentProfile");
		UserAuth userAuth = profileBean.getUserAuth();

		EditProfileBean editProfileBean = new EditProfileBean();
		String interests = userAuth.getProfile().getInterests();
		if (interests != null) {
			StringTokenizer tokenizer = new StringTokenizer(userAuth
					.getProfile().getInterests(), ",", true);
			List<String> interestList = new ArrayList();
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				interestList.add(token);
			}
			editProfileBean.setInterestsList(interestList);
			getSessionScope().put("editProfileBean", editProfileBean);
		}

		return "success";
	}


	/**
	 * This function is used to crop,encode the image provided by user before saving it to the file system.
	 * @param inputStream InputStream
	 * @param file String
	 */
	private void saveCropedFile(InputStream inputStream, String file) {
		BufferedImage image = cropImage(inputStream);
		saveImageToDisk(image, file);
	}

	/**
	 * It scale the image for a particular size.
	 * @param inputStream  InputStream
	 * @return BufferedImage
	 */
	private BufferedImage cropImage(InputStream inputStream) {
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
	private void saveImageToDisk(BufferedImage bi, String str) {
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
}
