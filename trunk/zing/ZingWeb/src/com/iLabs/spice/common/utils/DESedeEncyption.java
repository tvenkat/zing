/*
 * Copyright 2008 Impetus Infotech.
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
package com.iLabs.spice.common.utils;

import javax.crypto.spec.DESedeKeySpec;

/**
 * This is the encrytion class that provides functionality for encrypting and
 * decrypting the information using Triple DES.
 * 
 */
public class DESedeEncyption {

	/**
	 * This is the byte array containing the key specification data as specified
	 * in the configuration file.
	 */
	private static byte[] keySpecDESedeData = null;

	private DESedeEncyption() {

	}

	/**
	 * This method initializes the class by reading the key specification from
	 * the configuration file and setting it the key specification byte array.
	 */
	private synchronized static void initialize() {

	}

	/**
	 * This method generates a DESede key specification from the key
	 * specification data
	 * 
	 * @return DESedeKeySpec This is the key specification generated from the
	 *         key specification data
	 */
	private static DESedeKeySpec getDESedeKeySpecification() {
		return null;
	}

	/**
	 * This method takes the String parameter and returns encrypted value as a
	 * String. It would use Triple DES or DESede (168 bits) to perform the
	 * encryption, and would additionally perform 64-bit encoding to prevent
	 * string mangaling.
	 * 
	 * @param plainText
	 *            This is the string that has to be encrypted
	 * @return String This is the encypted information.
	 */
	public static String encrypt(String plainText) {
		return null;
	}

	/**
	 * This method takes the String parameter and returns decrypted value as a
	 * String. This method performs 64-bit decoding to convert the encypted data
	 * in a form that can be utilized by Triple DES or DESede (168 bits)
	 * decryption process.
	 * 
	 * @param encryptedData
	 *            This is the encrypted string for decryption
	 * @return String This is the decrypted information
	 */
	public static String decrypt(String encryptedData) {
		return null;
	}

}
