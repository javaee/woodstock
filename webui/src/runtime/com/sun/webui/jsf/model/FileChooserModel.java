/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.webui.jsf.model;

import java.io.Serializable;
import java.io.File;
import java.io.FileFilter;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.MissingResourceException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.sun.webui.jsf.component.FileChooser;

import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.util.ComponentUtilities;
import com.sun.webui.jsf.util.FilterUtil;
import com.sun.webui.jsf.util.ThemeUtilities;

// FIXME: Should be logging errors that don't have
// a way of bubbling up, like messages that don't accept arguments.
//
/**
 *
 * @author deep
 */
public class FileChooserModel implements ResourceModel, Serializable {

    private static final String WINDOWS_OS = "window";
    private static final String SPACE = String.valueOf('\u00a0');

    // These need to be configurable in the Theme
    //
    private static final String WINDOWS_ROOT = "c:\\";
    private static final String UNIX_ROOT = "/";
    private static final String DEFAULT_SERVER = "localhost";
    
    private String root = null;
    private String separatorString = File.separator;
    private String currentDir = null;
    private String filterValue = "*";
    private String sortValue = null;
    private boolean folderChooser = false;
    private boolean typeSet = false;
    private String serverName = null;

    private transient Collator collator = null;
    // private transient Theme theme = null;

    
    /** Creates a new instance of FileChooserModel */
    public FileChooserModel() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        // theme = ThemeUtilities.getTheme(context);
        

        if (isWindows()) {
            root = WINDOWS_ROOT;
	} else {
            root = UNIX_ROOT;
        }

        try {
            serverName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ukhe) {
            serverName = DEFAULT_SERVER;
        }
        
    }

    private boolean isWindows() {
        String osName = System.getProperty("os.name").toUpperCase(); //NOI18N
        return osName.startsWith(WINDOWS_OS.toUpperCase());
    }
            
    // FIXME: This should be get/setRoot.
    // Don't need "absolute".
    //
    /**
     * Returns the root value of the file system in question.
     * For example, in the default implementation of this interface for local
     * filesystems the root value would be "/" in Unix and "C:\" on Windows.
     *
     * @return returns the absolute root (directory for files and folders).
     */
    public String getAbsoluteRoot() {
        
        return this.root;
    }


    /**
     * Return all available roots.
     */
    public String[] getRoots() {

	File[] roots = File.listRoots();

	// In case we support just "\" as an absolute path on Windows
	// without the drive letter, add the separator as a root.
	//
	int len = roots.length;
	boolean isWindows = isWindows();
	if (isWindows) {
	    ++len;
	}
	String[] strRoots = new String[len];
	for (int i = 0; i < roots.length; ++i) {
	    strRoots[i] = roots[i].getPath();
	}
	if (isWindows) {
	    strRoots[len] = File.separator;
	}
	return strRoots;
    }
    
        
    /**
     * Sets the root value of the resource system in question.
     * For example, in the default implementation of this interface for local
     * filesystems the root value could be set to "/" in Unix and "C:\" on Windows.
     *
     * @param absRoot - the value to be used as the root of this resource system
     */
    public void setAbsoluteRoot(String absRoot) {
        
        if (absRoot != null) {
            this.root = absRoot;
        }
    }
    
    // These types of methods are not useful.
    // If any editing is required it must be part of the
    // interface like "dirname" and "basename"
    // However they may be needed for modification of
    // values on the client in javascript.
    //
    /**
     * Return the separator String for this resource system. For a 
     * file system chooser this would be File.separator.
     *
     * @return returns the separator String.
     */
    public String getSeparatorString() {
        
        return this.separatorString;
        
    }
    
    /**
     * Get the Server namefrom where the resources are being loaded.
     *
     * @return the server name
     * 
     */
    public String getServerName() {
        
        return serverName;
        
    }
    
     /**
     * Set the server name from where the resources are being loaded.
     *
     * @param serverName - the server name to be set
     * 
     */
    public void setServerName(String serverName) {
        
        if (serverName != null) {
            this.serverName = serverName;
        }
        
    }
    
    /**
     * Return the filter String currently in use.
     *
     * @return returns the filter String.
     */
    public String getFilterValue() {
        return this.filterValue;
    }
    
        
    /**
     * Set the filter String entered by the user in the Filter text field.
     *
     * @param filterValue - the filter string to be used subsequently.
     * 
     */
    public void setFilterValue(String filterValue) {
        if (filterValue != null) {
	    validateFilterValue(filterValue);
            this.filterValue = filterValue;
        }
    }
    
        
    /**
     * Return the sort field that is currently active.
     * 
     * @return returns the sort field in use.
     */
    public String getSortValue() {
        
        return this.sortValue;
        
    }
    
        
    /**
     * Set the sort field chosen by the user from the drop down menu.
     * 
     * @param sortValue - string representing sortValue selected by the user.
     * 
     */
    public void setSortValue(String sortValue) {
        if (sortValue != null) {
	    validateSortValue(sortValue);
            this.sortValue = sortValue;
        }
    }
    
    /**
     * This method is called to get the current directory of
     * the resuource list being displayed in the filechooser's listbox
     * If the current directory has not been set, root directory
     * is returned by calling <code>getAbsoluteRoot()</code>.
     *
     * @return returns the current directory or the root directory.
     */
    public String getCurrentDir() {
	if (this.currentDir != null) {
	    return this.currentDir;
	} else {
	    return getAbsoluteRoot();
	}
    }
    
    
    /**
     * This method is called to set the current directory of
     * the resuource list that would be displayed in the next
     * display cycle.
     *
     * @param dir - the value to be set the new current root node.
     * 
     */
    public void setCurrentDir(String dir) throws ResourceModelException {

	// How about returning the previous value ?
	// And why silence for null dir ? Use default ?
	//
        if (dir != null && dir.length() > 0) {
	    validateFolder(dir);
            this.currentDir = dir;
        }
    }
    
    /**
     * Returns the list of files in the directory represented by the
     * <code>folder</code> parameter. If <code>folder</code> is null
     * the contents of folder returned by <code>getCurrentDir</code>
     * are returned. This method returns an Array of ResourecItem objects
     * 
     * @return returns the contents of folder or the current directory.
     */
    public ResourceItem[] getFolderContent(String folder,
	    boolean disableFiles, boolean disableFolders) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Vector optList = new Vector();
        FileChooserItem[] fileEntries = null;
        FilterUtil filter = new FilterUtil(getFilterValue());
        boolean filesExist = false;
        
	if (folder == null) {
	     folder = getCurrentDir();
	}
        if (folder == null) {
            return null;
        }
        
        File file = new File(folder);
        File[] fileList = file.listFiles();
        if ((fileList == null)|| (fileList.length == 0)) { 
            return null;
        }
        
        // As per SWAED guidelines the list of folders should appear before the 
        // list of files. Hence, we need to sort the dirs followed by the files  
        // and then append the two arrays. 
                                                                                              
        // read each entry from the array and store it in one
        // of the two arraylists based on whether its a file or dir
                                                                                
        ArrayList justFiles = new ArrayList();
        ArrayList justDirs = new ArrayList();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                justDirs.add(fileList[i]);
            } else {
                justFiles.add(fileList[i]);
            }
        }
             
        File[] fileArray =  new File[justFiles.size()];
        for (int i = 0; i< justFiles.size(); i++) {
            fileArray[i] = (File) justFiles.get(i);
                   
        }
         
        File[] dirArray = new File[justDirs.size()];
        for (int i = 0; i< justDirs.size(); i++) {
            dirArray[i] = (File) justDirs.get(i);
        }
                   
        // sort the two files arrays
                   
        sort(fileArray, getSortValue());
        sort(dirArray, getSortValue());

        // merge the two files
                   
        int count = 0;
        for (; count < justDirs.size(); count++) {
            fileList[count] = dirArray[count];
        }
        for (int j = 0; j < justFiles.size(); j++) {
            fileList[count + j] = fileArray[j];
        }

        // end of code change to list of sorted dirs before files.
        
        for (int i = 0; i < fileList.length; i++) {
            String name = fileList[i].getName();
            boolean bSelectable = false;
            boolean bIsDirectory = fileList[i].isDirectory();
            boolean disabled = false;
            
            if (!bIsDirectory) {
                if (filter.accept(fileList[i])) {
                    bSelectable = true;
                    // if folderchooser then files should look as
                    // if they are disabled.
                    if (disableFiles) {
                        disabled = true;
                    }
                } else {
                    continue;
                }
            } else {
                // all folders are selectable
                bSelectable = true;
            }
            FileChooserItem item = getItem(fileList[i], context, disabled);
            optList.addElement(item);
            filesExist = true;
        }
        if (filesExist) {
            fileEntries = new FileChooserItem[optList.size()];
            for (int i=0; i< optList.size(); i++) {
                fileEntries[i] = (FileChooserItem) optList.elementAt(i);
            }
            return fileEntries;
        } else {
            return null;
        }
    }
    
    /**
     * Given a ResourceItem key return the ResourceItem.
     *
     * @param  itemKey the resource item key which is the same as the value
     * of the Option element in the listbox.
     *
     * @return the ResourceItem object
     */
    public ResourceItem getResourceItem(String itemKey) {
        
        FacesContext context = FacesContext.getCurrentInstance();
        String resource = null;
        String [] strArray = itemKey.split("=");
        if (strArray == null) {
            return null;
        }
        if (strArray.length == 2) {
            resource = strArray[1];
        } else {
            resource = strArray[0];
        }
        File f = new File(resource);
        return getItem(f, context, false);
    }

    // FIXME: Don't need "Type" should be just "isFolder".
    //

    /**
     * Returns true if the supplied absolute path is a folder type.
     * 
     * @param  path - the absolute path to the resource
     * @return returns the current root (directory for files and folders).
     */
     public boolean isFolderType(String path) {
         File f = new File(path);
         return f.isDirectory();
     }
     
    /**
     * This methods checks if the resource path in question can be accessed
     * by the user trying to select or view it.
     *
     * @param resourceName - the resource name to check for read access
     * @return true if the user can select the resource specified
     *  by the resource name.
     */
    public boolean canRead(String resourceName) {
        
        File f = new File(resourceName);
        return f.canRead();
        
    }
    
        
    /**
     * This methods checks if the resource path in question can be accessed
     * for writes by the user.
     *
     * @param resourceName - the resource name to check for write access
     * @return true if the user can select the resource specified
     *  by the resource name for write.
     * 
     */
    public boolean canWrite(String resourceName) {
        
        File f = new File(resourceName);
        return f.canWrite();
    }
    
    /////////////////////////////////////////////////////////////////////
    // private methods 
    /////////////////////////////////////////////////////////////////////
    

    // FIXME: This formatting should be a theme based format spec.
    // 
    /**
     * Create and return an Option object representing en entry for
     * the listbox.
     */
    protected FileChooserItem getItem(File file, FacesContext context, 
            boolean disabled) {
        
        Theme theme = getTheme();
        int fileNameLen = Integer.parseInt(theme
                .getMessage("filechooser.fileNameLen")); // NOI18N
        int fileSizeLen = Integer.parseInt(theme
                .getMessage("filechooser.fileSizeLen")); // NOI18N
        int fileDateLen = Integer.parseInt(theme
                .getMessage("filechooser.fileDateLen")); // NOI18N
        
        Locale locale = context.getViewRoot().getLocale();
       
        SimpleDateFormat dateFormat = null;
	// If there is a default localized pattern in the theme use it.
	//
	try {
	    String defaultPattern = ThemeUtilities.getTheme(context).getMessage(
		"filechooser.dateFormat");
	    dateFormat = new SimpleDateFormat(defaultPattern, locale);
	} catch (Exception e) {
	    // Either the property is not defined in the theme
	    // or it was an invalid format.
	    //
	    dateFormat = (SimpleDateFormat)
		SimpleDateFormat.getDateInstance(DateFormat.SHORT, locale);
	    String pattern = ComponentUtilities.getDefaultDatePattern(
		dateFormat.toPattern(), locale);
	    dateFormat.applyPattern(pattern);
	}
                
	// If the themed time format fails, from bad formatting
	// just use "HH:mm".
	//
	SimpleDateFormat timeFormat = null;
	try {
	    String tf = theme.getMessage("filechooser.timeFormat");
	    timeFormat = new SimpleDateFormat(tf, locale);
	} catch (Exception e) {
	    // Either the property is not defined in the theme
	    // or it was an invalid format.
	    //
	    timeFormat = (SimpleDateFormat)
		SimpleDateFormat.getTimeInstance(DateFormat.SHORT, locale);
	    // We need to remove the "AM/PM" marker.
	    // This is not a "generic" time format like the date format
	    // above so we do it here vs. as a helper.
	    //
	    String pattern = timeFormat.toPattern();
	    pattern = pattern.replaceFirst("[ ]*a[ ]*", "");
	    pattern = pattern.replaceFirst("[hH]+", "HH");
	    timeFormat.applyPattern(pattern);
	}

        String name = file.getName();
        String value = null;
        if (file.isDirectory()) {
            name+= File.separator;
            value = "folder" + "=" + file.getAbsolutePath();   //NOI18N
        } else {
            value = "file" + "=" + file.getAbsolutePath();    //NOI18N
        }
        name = getDisplayString(name, fileNameLen);
        String size = Long.toString(file.length());
        size = getDisplayString(size, fileSizeLen);
        Date modifiedDate = new Date(file.lastModified());
        String date = dateFormat.format(modifiedDate);
        String time = timeFormat.format(modifiedDate);
        StringBuffer buffer = new StringBuffer(128);
        buffer.append(name)
        .append(SPACE)
        .append(SPACE)
        .append(SPACE)
        .append(size)
        .append(SPACE)
        .append(SPACE)
        .append(date)
        .append(SPACE)
        .append(time);
        FileChooserItem item = new FileChooserItem(file);
        item.setItemKey(value);
        item.setItemLabel(buffer.toString());
        item.setItemDisabled(disabled);
        return item;
    }
    
    /**
     * This method returns the string of size maxLen by padding the
     * appropriate amount of spaces next to str.
     */
    private String getDisplayString(String str, int maxLen) {
        
        int length = str.length();
        if (length < maxLen) {
            int spaceCount = maxLen - length;
            for (int j = 0; j < spaceCount; j++) {
                str += SPACE;
            }
        } else if (length > maxLen) {
            int shownLen = maxLen - 3;
            str = str.substring(0, shownLen);
            str += "...";    //NOI18N
        }
        return str;
    }
    
    
        interface SortRule {
	public boolean compare(File f1, File f2);
    }

    /**
     * This module sorts the files and dirs in a given directory
     * according to the sort field selected by the user.
     * By default the files will be sorted
     * alphabetically with all folders first, followed by all files in
     * alphabetical order. If the sort field is TIME the file will be
     * sorted in ascending order with the earliest modified file first.
     * if the sort field is SIZE, the files will be sorted by size with
     * the smallest first. The bubble sort algorithm is being used here.
     * @param fileList array of files to be sorted
     * @param sortValue ths field to be sorted oin
     */
    protected void sort(File[] fileList, String sortValue) {

        if ((fileList == null) || (fileList.length == 0))
            return;

        if (fileList.length == 1)
            return;

	FacesContext context = FacesContext.getCurrentInstance();
	Locale locale = context.getViewRoot().getLocale();
	collator =  Collator.getInstance(locale);
	collator.setStrength(Collator.SECONDARY);

        if (sortValue == null) {
            sortValue = FileChooser.ALPHABETIC_ASC;
        }
        
        // check if the two files being compared are already
        // in the desired order

  	SortRule sr = null;
  	if (sortValue.equals(FileChooser.ALPHABETIC_ASC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
		    return (collator.compare(file1.getName(), 
			file2.getName()) >= 0);
  		}
  	    };
  	} else if (sortValue.equals(FileChooser.ALPHABETIC_DSC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
		    return (collator.compare(file2.getName(), 
			file1.getName()) >= 0);
  		}
  	    };
  	} else if (sortValue.equals(FileChooser.SIZE_ASC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
        	    if (file1.length() == file2.length()) {
		        return (collator.compare(file1.getName(), 
			    file2.getName()) >= 0);
        	    } else {
            		return (file1.length() > file2.length());
        	    }
  		}
  	    };
  	} else if (sortValue.equals(FileChooser.SIZE_DSC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
        	    if (file1.length() == file2.length()) {
		        return (collator.compare(file1.getName(), 
			    file2.getName()) >= 0);
        	    } else {
            		return (file1.length() < file2.length());
        	    }
  		}
  	    };
  	} else if (sortValue.equals(FileChooser.LASTMODIFIED_ASC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
        	    if (file1.lastModified() == file2.lastModified()) {
		        return (collator.compare(file1.getName(), 
			    file2.getName()) >= 0);
        	    } else {
            		return (file1.lastModified() > file2.lastModified());
        	    }
  		}
  	    };
  	} else if (sortValue.equals(FileChooser.LASTMODIFIED_DSC)) {
  	    sr = new SortRule() {
  		public boolean compare(File file1, File file2) {
        	    if (file1.lastModified() == file2.lastModified()) {
		        return (collator.compare(file1.getName(), 
			    file2.getName()) >= 0);
        	    } else {
            		return (file1.lastModified() < file2.lastModified());
        	    }
  		}
  	    };
  	}
  
  	// sort the file using bubble sort
	for (int i = fileList.length -1 ; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
  
		// if not swap them
                if (sr.compare(fileList[j], fileList[j+1])) {
                    File tmp = fileList[j];
                    fileList[j] = fileList[j+1];
                    fileList[j+1] = tmp;
                  }
	    }
  	}
    }

    // This was returning "getCurrentDir" on exception and if there
    // was no parent. This can result in an infinite loop
    // if this method is naviget up the hierarchy.
    // return null if there is no parent.
    //
    /**
     * Return the parent folder of the value of <code>getCurrentDir</code>.
     * If the current directory does not have a parent
     * null is returned.
     */
    public String getParentFolder() {

	File parent = null;
	String parentDir = null;
	try {
	    // Create a file object from the lookIn folder.
	    //
	    parent = new File(getCurrentDir());
	    parentDir = parent.getParent();
	} catch (Exception e) {
	    // Does not have a parent, returns null.
	}
	return parentDir;
    }

    // This is really a function of platform. And configurable.
    //
    public String getEscapeChar() {
       return getSeparatorString().equals("/") ? "\\" : "/"; //NOI18N
    }
    public String getDelimiterChar() {
       return ","; //NOI18N
    }

    // Currently this is the only validation done for folders.
    // Actually this should be mode detailed, like doesn't
    // exist, no permission, etc.
    //
    protected void validateFolder(String folder)
	    throws ResourceModelException {

	if (!canRead(folder)) {
	    throw new ResourceModelException(
		createFacesMessage("filechooser.cannotCompleteErrSum",
		"filechooser.cannot_read_folder",
		null, new String [] {folder}));
	}
    }

    // Currently no validation for the sort value.
    //
    protected void validateSortValue(String sortValue)
	    throws ResourceModelException {
    }

    // Currently no validation for the filter value.
    //
    protected void validateFilterValue(String filterValue)
	    throws ResourceModelException {
    }

    /**
     * Return a File[] of the selections in content validating the
     * the entries.
     */
    public Object[] getSelectedContent(String[] content,
		boolean selectFolders) 
	    throws ResourceModelException {

        // Contents of Selected File textfield could be a list of files
        // separated by commas. Parse the list, prepend the folder name
        // if required and add them to the list of selected files.
        // For a single entry check if it is a file or a folder.
        // If folder, open this folder and enter folder name in
        // Look In field. If file, prepend folder name if required
        // and it to the selected files list.

	// Create the parent File object and pass it to the
	// appropriate method.
	//
	File parent = null;
	try {
	    parent = new File(getCurrentDir());
	} catch (Exception e) {
	    // Convert to a ResourceModelException with the
	    // cause.
	    //
	    throwException(e, "filechooser.cannotCompleteErrSum", //NOI18N
		selectFolders ?
			"filechooser.folderSelectError" : //NOI18N
			"filechooser.fileSelectError", //NOI18N
		null, null);
	}
	if (selectFolders) {
	    return getSelectedFolders(parent, content);
	} else {
	    return getSelectedFiles(parent, content);
	}
    }

    // There is an odd policy from the guidelines implementd here.
    // If there is one selection and it is a folder, relative or
    // absolute path, it is set as the current directory.
    // If it is a folder chooser, it also becomes the value.
    // If it is a file chooser, it is not accepted as a value,
    // just as a directory change.
    //

    /**
     * Return a File array of the selected folders from the
     * parent directory. If there are no selections, an empty
     * array is returned.
     *
     * Typically the parent is the current directory or the look in
     * folder value and folders is comprised of relative paths, relative
     * to parent, but they can be absolute paths.
     *
     * This method validates the folder entries. This means
     * the "File.canRead" method must return true and the
     * entry must be a folder and not a file.
     *
     * If there is one selection and it is a folder, relative or
     * absolute path, it is set as the current directory.
     * 
     * ResourceModelException are thrown on error.
     */
    protected File[] getSelectedFolders(File parent, String[] folders) 
	    throws ResourceModelException {

	// If a selected folder is not an absolute path
	// it must be a child of the lookIn folder.
	// If it does not exist throw FileChooserModelExeption.
	// If it exists it must be a folder if it's not throw
	// FileChooserModelExeption.
	//
	ArrayList folderArray = new ArrayList();
	for (int i = 0; i < folders.length; ++i) {

	    // For general exceptions
	    //
	    try {
		File folder = new File(folders[i]);
		// If its not an absolute path it must be a child entry
		// of the parent.
		//
		if (!folder.isAbsolute()) {
		    folder = new File(parent, folders[i]);
		}
		// It must exist
		// Currently we only have an error message for "canRead".
		//
		if (!folder.canRead()) {
		    throw new ResourceModelException(
			createFacesMessage(
			    "filechooser.cannotCompleteErrSum", //NOI18N
			    "filechooser.cannot_read_selected_folder", //NOI18N
			    null, new String [] {folders[i]}));
		}
		// It must be a folder
		// FIXME: This error should include an argument to let the
		// user know which choice was a file.
		//
		if (!folder.isDirectory()) {
		    throw new ResourceModelException(
			createFacesMessage(
			    "filechooser.cannotCompleteErrSum", //NOI18N
			    "filechooser.cannotSelectFile", //NOI18N
			    null, null));
		}

		folderArray.add(folder);

	    } catch (ResourceModelException fcme) {
		// Just pass it on
		//
		throw fcme;
	    } catch (Exception e) {
		// Convert to a ResourceModelException with the
		// cause.
		//
		throwException(e, "filechooser.cannotCompleteErrSum", //NOI18N
		    "filechooser.fileSelectError", //NOI18N
		    null, null);
	    }
	}
	return (File[])folderArray.toArray(new File[0]);
    }

    /**
     * Return a File array of the selected files from the
     * parent directory. If there are no selections, an empty
     * array is returned.
     *
     * Typically the parent is the current directory or the look in
     * folder value and files is comprised of relative paths, relative
     * to parent, but they can be absolute paths.
     *
     * This method validates the file entries. This is currently 
     * that the "File.canRead" method must return true and the
     * entry must be a file and not a folder, with one exception.
     *
     * If there is one selection and it is a folder, relative or
     * absolute path, it is set as the current directory and an 
     * empty File[] is returned.
     * 
     * ResourceModelException are thrown on error.
     */
    protected File[] getSelectedFiles(File parent, String[] files) {

	// If a selected file is not an absolute path
	// If it is an absolute path, capture the first
	// selection that is a full path and record the
	// parent directory. When complete set the current directory
	// to that folder.
	// it must be a child of the lookIn folder.
	// If it does not exist throw FileChooserModelExeption.
	// If it exists it must be a file, if it's not a file
	// throw FileChooserModelExeption
	//
	String newCurrentDir = null;
	ArrayList filesArray = new ArrayList();
	for (int i = 0; i < files.length; ++i) {

	    // For General Exception catching
	    //
	    try {
		File file = new File(files[i]);
		// If it's not an absolute path it must be a child entry
		// of the parent.
		//
		if (!file.isAbsolute()) {
		    file = new File(parent, files[i]);
		} else {
		    if (newCurrentDir == null) {
			newCurrentDir = file.getParent();
		    }
		}
		// It must exist
		// Currently we only have an error message for "canRead".
		//
		if (!file.canRead()) {
		    throw new ResourceModelException(
			createFacesMessage(
			    "filechooser.cannotCompleteErrSum", //NOI18N
			    "filechooser.cannot_read_selected_file", //NOI18N
			    null, new String [] {files[i]}));
		}
		// It must be a file
		// FIXME: This error should include an argument to let the
		// user know which choice was a folder.
		//
		if (file.isDirectory()) {
		    // Special case.
		    // If there is only one selection and it is a
		    // directory, use it to set the current directory
		    // and do not return it as a value.
		    //
		    // This may have set newCurrentDir
		    //
		    newCurrentDir = null;
		    if (files.length == 1) {
			setCurrentDir(file.getPath());
			break;
		    }
		    throw new ResourceModelException(
			createFacesMessage(
			    "filechooser.cannotCompleteErrSum", //NOI18N
			    "filechooser.cannotSelectFolder", //NOI18N
			    null, null));
		}

		filesArray.add(file);

	    } catch (ResourceModelException fcme) {
		// Just pass it on
		//
		throw fcme;
	    } catch (Exception e) {
		// Convert to a ResourceModelException with the
		// cause.
		//
		throwException(e, "filechooser.cannotCompleteErrSum", //NOI18N
		    "filechooser.folderSelectError", //NOI18N
		    null, null);
	    }
	}
	// If we have a new parent dir set it
	//
	if (newCurrentDir != null) {
	    setCurrentDir(newCurrentDir);
	}
	return (File[])filesArray.toArray(new File[0]);
    }

    private void throwException(Exception e, String summKey,
	    String detKey, String[] summArg, String[] detArg)
	    throws ResourceModelException {

	FacesMessage fmsg = createFacesMessage(summKey, detKey, summArg,
		detArg);
	Throwable cause = e.getCause();
	if (cause != null) {
	    throw new ResourceModelException(fmsg, cause);
	} else {
	    throw new ResourceModelException(fmsg);
	}
    }

    /**
     * This method creates a FacesMessage.
     * @param summary The error message summary
     * @param detail The error message detail
     */
    private FacesMessage createFacesMessage(String summary,
	    String detail, String [] summaryArgs, String [] detailArgs) {

	Theme theme = getTheme();
	String summaryMsg = theme.getMessage(summary, summaryArgs);
	String detailMsg = theme.getMessage(detail, detailArgs);
	FacesMessage fmsg = new FacesMessage(
	    FacesMessage.SEVERITY_ERROR,
	    summaryMsg, detailMsg);
	return fmsg;
    }
    
    /*
     * Get the theme
     */
    private Theme getTheme() {
        
       FacesContext context = FacesContext.getCurrentInstance();
       return ThemeUtilities.getTheme(context);
    }

}
