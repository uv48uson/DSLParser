package io;

import static java.nio.file.FileVisitResult.CONTINUE;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class RegisterFiles
    extends SimpleFileVisitor<Path> {
	private ResourceHandler resourcehandler;
	
	public RegisterFiles(ResourceHandler resourcehandler) {
		this.resourcehandler = resourcehandler;
	}

    // Print information about
    // each type of file.
    @Override
    public FileVisitResult visitFile(Path file,
                                   BasicFileAttributes attr) {
    	
        if (attr.isRegularFile()) {
        	if (file.toString().contains(".mydsl")) {
				try {
					resourcehandler.getResourceFrom(file.toFile(), false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
        }
        
        return CONTINUE;
    }

    // If there is some error accessing
    // the file, let the user know.
    // If you don't override this method
    // and an error occurs, an IOException 
    // is thrown.
    @Override
    public FileVisitResult visitFileFailed(Path file,
                                       IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}