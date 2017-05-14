package com.shframework.common.resolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class PathResolver extends PathMatchingResourcePatternResolver {

	@Override
	protected Resource convertClassLoaderURL(URL url) {
		// TODO Auto-generated method stub
		return super.convertClassLoaderURL(url);
	}
	
	@Override
	protected String determineRootDir(String location) {
		// TODO Auto-generated method stub
		return super.determineRootDir(location);
	}
	
	@Override
	protected Set<Resource> doFindMatchingFileSystemResources(File rootDir, String subPattern) throws IOException {
		// TODO Auto-generated method stub
		return super.doFindMatchingFileSystemResources(rootDir, subPattern);
	}
	
	@Override
	protected Set<Resource> doFindPathMatchingFileResources(
			Resource rootDirResource, String subPattern) throws IOException {
		// TODO Auto-generated method stub
		return super.doFindPathMatchingFileResources(rootDirResource, subPattern);
	}
	
	@Override
	protected Set<Resource> doFindPathMatchingJarResources(Resource rootDirResource, String subPattern) throws IOException {
		// TODO Auto-generated method stub
		return super.doFindPathMatchingJarResources(rootDirResource, subPattern);
	}
	
	@Override
	protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<File> result) throws IOException {
		// TODO Auto-generated method stub
		super.doRetrieveMatchingFiles(fullPattern, dir, result);
	}
	
	@Override
	protected Resource[] findAllClassPathResources(String location) throws IOException {
		// TODO Auto-generated method stub
		return super.findAllClassPathResources(location);
	}
	
	@Override
	protected Resource[] findPathMatchingResources(String locationPattern) throws IOException {
		// TODO Auto-generated method stub
		return super.findPathMatchingResources(locationPattern);
	}
	
	@Override
	protected Set<File> retrieveMatchingFiles(File rootDir, String pattern) throws IOException {
		// TODO Auto-generated method stub
		return super.retrieveMatchingFiles(rootDir, pattern);
	}
	
	@Override
	protected Resource resolveRootDirResource(Resource original) throws IOException {
		// TODO Auto-generated method stub
		return super.resolveRootDirResource(original);
	}
}
