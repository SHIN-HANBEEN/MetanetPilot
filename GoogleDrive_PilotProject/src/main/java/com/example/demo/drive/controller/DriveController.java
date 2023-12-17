package com.example.demo.drive.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.drive.service.IDriveService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@RestController
@RequestMapping("/drive")
public class DriveController {
	@Autowired
	IDriveService driveService;
	
	private static final Logger logger = LoggerFactory.getLogger(DriveController.class);
	
	//테스트용도 getDirPath(String dirId) 해보기
	@GetMapping("/test")
	public String getDirPath(@RequestParam String dirid) {
		logger.info("=================test activate=================");
		return driveService.getDirPath(dirid);
	}
	
	//List<DirPath> getHomeOfMember(int memberid);
	//사용자별 home 가져오기
	@GetMapping("/home")
	public List<Map<String, Object>> getHomeOfMember(Principal principal) { //principal 로 하니까, interceptor 필요 없음
		logger.info("=================getHomeOfMember Get activate=================");
		System.out.println("principal getName : " + principal.getName());
		return driveService.getHomeOfMember(principal.getName());
	}
	
	//List<DirPath> getSubDirectory(String parentdirid);
	//사용자가 클릭했을 때 폴더 안의 내용물 보여주기
	@GetMapping("/getsub")
	public List<Map<String, Object>> getSubDirectory(HttpSession session) {
		logger.info("=================getHomeOfMember Get activate=================");
		String dirId = (String)session.getAttribute("dirId");
		return driveService.getSubDirectory(dirId);
	}
	
//	//다운로드
//	@GetMapping("/downloadfile")
//	public void downloadFile(HttpSession session, HttpServletResponse response) {
//		logger.info("=================downloadFile Get activate=================");
//		
//		try {
//			Map<String, String> fileInfo = driveService.getPath((String)session.getAttribute("dirId"));
//			byte[] files = FileUtils.readFileToByteArray(new File(fileInfo.get("fullPath")));
//			response.setContentType("application/octet-stream");
//            response.setContentLength(files.length);
//            response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(fileInfo.get("fileName"),StandardCharsets.UTF_8)+"\";");
//            response.setHeader("Content-Transfer-Encoding","binary");
//
//            response.getOutputStream().write(files);
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			//https://velog.io/@on5949/SpringBoot%ED%8C%8C%EC%9D%BC-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C%EC%9D%98-%EB%91%90%EA%B0%80%EC%A7%80-%EB%B0%A9%EB%B2%95
//		}
//	}
	//다운로드
		@GetMapping("/downloadfile/{dirid}")
		public void downloadFile(@PathVariable String dirid, HttpServletResponse response) {
			logger.info("=================downloadFile Get activate=================");
			//return driveService.downloadFile(dirid);
			try {
				Map<String, String> fileInfo = driveService.getPath(dirid);
				byte[] files = FileUtils.readFileToByteArray(new File(fileInfo.get("fullPath")));
				response.setContentType("application/octet-stream");
	            response.setContentLength(files.length);
	            response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(fileInfo.get("fileName"),StandardCharsets.UTF_8)+"\";");
	            response.setHeader("Content-Transfer-Encoding","binary");

	            response.getOutputStream().write(files);
	            response.getOutputStream().flush();
	            response.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//https://velog.io/@on5949/SpringBoot%ED%8C%8C%EC%9D%BC-%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C%EC%9D%98-%EB%91%90%EA%B0%80%EC%A7%80-%EB%B0%A9%EB%B2%95
			}
		}
	
	//파일 업로드
	@PostMapping(path = "/uploadfile", consumes = "multipart/form-data", produces = "application/json")
	public String uploadFile(Principal principal, HttpSession session) {
		logger.info("================= uploadFile Post activate =================");
		System.out.println("principal.getName : " +  principal.getName());
		System.out.println("sesstion.dirId : " +  (String)session.getAttribute("dirId"));
		System.out.println("sesstion.file : " +  (MultipartFile)session.getAttribute("file"));
		driveService.uploadFile(principal.getName(), (String)session.getAttribute("dirId"), (MultipartFile)session.getAttribute("file"));
		return "uploadfile success";
	}
	
   @PostMapping(path = "/folders/{uuid}", consumes = "multipart/form-data", produces = "application/json")
   public void uploadFile(@RequestParam(value = "file", required = false) MultipartFile multipartFile, @PathVariable String uuid,
           Principal principal) {
      System.out.println(principal.getName());
      System.out.println(multipartFile.isEmpty());
      System.out.println(multipartFile.getName());
      
      boolean uploadFile = driveService.uploadFile(principal.getName(), uuid, multipartFile);
   }
	
	
	
	@PostMapping("/drive/makefolder/{uuid}")
	public void makeFolder(@PathVariable String uuid, Principal principal, @RequestBody String folderName) {
		driveService.makeFolder(principal.getName(), uuid, folderName);
	}
	
	@DeleteMapping("/drive/folders/{uuid}/{fileName}")
	public void deleteFileOrFolder(@PathVariable String uuid, @PathVariable String fileName, Principal principal) {
		boolean deleteFileCascade = driveService.deleteFileCascade(principal.getName(), uuid, fileName);
	}
	
	
	
	
	
	
	//@GetMapping("/")
	
	

	
	//로그인 성공하면, 메인 화면으로
//	@GetMapping("/home")
//	public String getHome(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String memberId = auth.getName();
//		if (memberId != null && !memberId.equals("")) {
//			model.addAttribute("directoryList", driveService.getHomeDirectory(Integer.valueOf(memberId))); //List 가져온 것을 'directoryList' 로 model 에 담아서 반환
//			return "drive/home";
//		} else {
//			return "drive/home";
//		}
//	}
	

	//GET - /drive/home/ : 로그인 성공하면 홈으로
	//GET - /drive/folders/{UUID} : 조회
	//GET - /drive/folders/{UUID}/{fileName} : 폴더에서 file 클릭해서 다운로드 시작
	//POST - /drive/folders/{UUID} : 파일 업로드
	//POST - /drive/makefolder/{UUID} : 폴더 만들기
	//DELETE - /drive/folders/{UUID}/{fileName} : 파일, 폴더 삭제. 단, filename 은 비어있을 수 있게 처리. @RequestParam(required=false, defaultValue="")
}
