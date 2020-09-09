package br.com.confeitaria.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	
	void init();
	
	void save(MultipartFile file);
	
	Stream<Path> loadAll();
	
	Resource load(String filename);
	
	void deleteAll();

	void delete(String filename);

}
