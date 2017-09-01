package com.edassist.controller.content;

import com.edassist.models.contracts.content.FileContent;
import com.edassist.service.content.FileContentService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileContentController {

	private final FileContentService fileContentService;

	@Autowired
	public FileContentController(FileContentService fileContentService) {
		this.fileContentService = fileContentService;
	}

	@RequestMapping(value = "/v1/content/file/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> retrieveFileById(@PathVariable("id") String id) throws IOException, MimeTypeException {

		ByteArrayResource byteArrayResource = fileContentService.retrieveFileById(id);
		byte[] fileBytes = byteArrayResource.getByteArray();

		return new ResponseEntity<>(fileBytes, getHeaderData(fileBytes), HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/file", method = RequestMethod.POST)
	public ResponseEntity<Boolean> saveUpdateFileById(@RequestParam(value = "file") MultipartFile file, @RequestParam(required = false) String id, @RequestParam(required = false) String fileId)
			throws IOException {

		FileContent fileContent = new FileContent();
		fileContent.setGeneralContentId(id);
		fileContent.setFileName(FilenameUtils.removeExtension(file.getOriginalFilename()));
		fileContent.setFile(IOUtils.toByteArray(file.getInputStream()));

		return new ResponseEntity<>(fileContentService.saveOrUpdateFileById(fileContent), HttpStatus.OK);
	}

	private HttpHeaders getHeaderData(byte[] fileBytes) throws IOException, MimeTypeException {
		final HttpHeaders headers = new HttpHeaders();

		TikaInputStream tikaStream = TikaInputStream.get(fileBytes);
		Tika tika = new Tika();
		String mimeType = tika.detect(tikaStream);
		headers.setContentType(MediaType.valueOf(mimeType));

		MimeTypes defaultMimeTypes = MimeTypes.getDefaultMimeTypes();
		String extension = defaultMimeTypes.forName(mimeType).getExtension();
		headers.add("file-ext", extension);

		return headers;
	}

}
