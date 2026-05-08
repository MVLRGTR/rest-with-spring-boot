package file.importer.contract;

import java.io.InputStream;
import java.util.List;

import com.digitalmindkr.apirest.data.dto.v1.PersonDTO;


public interface FileImporter {
	
	List<PersonDTO> importFile(InputStream inputStream) throws Exception;

}
