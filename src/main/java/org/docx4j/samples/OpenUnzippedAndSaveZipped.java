/*
 *  Copyright 2012, Plutext Pty Ltd.
 *   
 *  This file is part of docx4j.

    docx4j is licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 

    You may obtain a copy of the License at 

        http://www.apache.org/licenses/LICENSE-2.0 

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.

 */

package org.docx4j.samples;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.io3.Load3;
import org.docx4j.openpackaging.io3.Save;
import org.docx4j.openpackaging.io3.stores.UnzippedPartStore;
import org.docx4j.openpackaging.io3.stores.ZipPartStore;
import org.docx4j.openpackaging.packages.OpcPackage;


/**
 * Example of loading an unzipped file from the file system.
 * 
 * @author jharrop
 *
 */
public class OpenUnzippedAndSaveZipped extends AbstractSample {
	
	public static JAXBContext context = org.docx4j.jaxb.Context.jc; 

	public static void main(String[] args) throws Exception {

		try {
			getInputFilePath(args);
		} catch (IllegalArgumentException e) {
	    	inputfilepath = System.getProperty("user.dir") + "/sample-docs/word/sample-docx.docx";
		}
		System.out.println(inputfilepath);	    	
		
		
		// Load the docx
		File baseDir = new File(System.getProperty("user.dir") + "/OUT"); 
		UnzippedPartStore partLoader = new UnzippedPartStore(baseDir);
		final Load3 loader = new Load3(partLoader);
		OpcPackage opc = loader.get();
		
		// Save it zipped
		File docxFile = new File(System.getProperty("user.dir") + "/zip.docx"); 
		
		ZipPartStore zps = new ZipPartStore();
		zps.setSourcePartStore(opc.getPartStore());
		
		Save saver = new Save(opc, zps);
		try {
			saver.save(new FileOutputStream(docxFile));
		} catch (FileNotFoundException e) {
			throw new Docx4JException("Couldn't save " + docxFile.getPath(), e);
		}
		
	}
		

}
