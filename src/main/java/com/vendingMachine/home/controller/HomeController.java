package com.vendingMachine.home.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//메인페이지
	@GetMapping("/main")
	public String mainPage(Model model) {
		
		return "/Lading/main";
	}
	
	@GetMapping("/qrMain") 
	public String makeQR(HttpServletRequest request,HttpSession session,String storeName) throws WriterException, IOException { 
		storeName = "김인용";
//		String root = request.getSession().getServletContext().getRealPath("resources"); //현재 서비스가 돌아가고 있는 서블릿 경로의 resources 폴더 찾기
		String path = System.getProperty("user.dir");
		logger.info("root : {} ", path);
		String savePath = path + "\\qrCodes\\"; // 파일 경로
		logger.info("savePath : {} ", savePath);
		//파일 경로가 없으면 생성하기
		File file = new File(savePath); 
		if(!file.exists()) { 
	   	 	file.mkdirs(); 
	   	 logger.info("생성");
		}  
	    
	    // 링크로 할 URL주소 
	    String url = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query="+storeName; 
	    url = "파스쿠찌"
	    		+ "\n"
	    		+ "김인용"
	    		+ "\n"
	    		+ "안산중앙점";
	     // 링크 생성값
	    String codeurl = new String(url.getBytes("UTF-8"), "ISO-8859-1"); 
	   
	    // QRCode 색상값
	    int qrcodeColor =   0xFF2e4e96; 
	    // QRCode 배경색상값  
	    int backgroundColor = 0xFFFFFFFF; 
	         
	    //QRCode 생성
	    QRCodeWriter qrCodeWriter = new QRCodeWriter();  
	    BitMatrix bitMatrix = qrCodeWriter.encode(codeurl, BarcodeFormat.QR_CODE,200, 200);    // 200,200은 width, height
	  
	    MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor); 
	    BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig); 
	    
	    //파일 이름에 저장한 날짜를 포함해주기 위해 date생성
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
	    String fileName=sdf.format(new Date()) +storeName; 
	    
	    //파일 경로, 파일 이름 , 파일 확장자에 맡는 파일 생성
	    File temp =  new File(savePath+fileName+".png");  
	    
	    // ImageIO를 사용하여 파일쓰기 
	    ImageIO.write(bufferedImage, "png",temp); 
		
	    //리턴은 사용자가 원하는 값을 리턴한다. 
	    //작성자는 QRCode 파일의 이름을 넘겨주고 싶었음.
		return fileName+".png"; 
	}
//	
//	public String createQR() throws Exception {
//        
//        BitMatrix bitMatrix=null;
//        MatrixToImageConfig matrixToImageConfig=null;
//        // QRCode에 담고 싶은 정보를 문자열로 표시한다. url이든 뭐든 가능하다.
//        String codeInformation = "";
//
//        // 큐알코드 바코드 및 배경 색상값
//        int onColor =   0xFF2e4e96; // 바코드 색
//        int offColor = 0xFFFFFFFF; // 배경 색
//        
//        // 이름 그대로 QRCode 만들때 쓰는 클래스다
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        // 큐알 전경과 배경의 색을 정한다. 값을 넣지 않으면 검정코드에 흰 배경이 기본값이다.
//        matrixToImageConfig = new MatrixToImageConfig(qrcodeColor,backgroundColor);
//        Map<EncodeHintType,String> hints =Generics.newHashMap();
//        // QRCode 생성시 조건을 넣어서 만들 수 있게 한다.
//        // 여기서 Error_Correction의 경우 큐알 코드가 좀더 자글자글하게 만들어 주는 대신 큐알이 가려져도 인식할 가능성이 더욱 높아진다.
//       
//       /*
// 	https://zxing.github.io/zxing/apidocs/com/google/zxing/qrcode/decoder/ErrorCorrectionLevel.html
//        Enum Constants
//        L = ~7% correction
//        M = ~15% correction
//        Q = ~25% correction
//        H = ~30% correction
//        */
//        hints.put(EncodeHintType.ERROR_CORRECTION,"L");
//        
//
//        // QRCode 전체 크기
//        // 단위는 fixel
//        int width=1000;
//        int height=1000;
//
//        // 내부에 빈 공간만들 빈 공간 -> oncolor로 만들어진다.
//        //int regionWidth=100;
//        //int regionHeight=100;
//
//        try {
//            // bitMatrix 형식으로 QRCode를 만든다.
//            bitMatrix = qrCodeWriter.encode(codeInformation, BarcodeFormat.QR_CODE,width, height);
//            // QRCode 중간에 빈공간을 만들고 색을 offColor로 바꿔주는 메소드
//	    // bitMatrix= emptyQR(bitMatrix,regionHeight,regionWidth); // QR내부에 빈 공간 만드는 메소드(사용할 경우 hint의 error_correction 을 반드시 높여줘야 합니다)
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream, matrixToImageConfig);
//        
//        // 이제 만들어본 QRCode를 저장해보자
//        String savePath = "c:\\test\\";
//        String saveFileName = "qrImage.png";
//            File file = new File(savePath);
//            if(!file.exists()) {
//                file.mkdirs();
//                // 리눅스 서버에 저장하는 경우 파일 접근 권한을 줘야 한다.
//            }
//            // 파일은 저장하고 싶은대로 저장하면 된다.
//            // buffer나 stream는 공부를 더 해봐야 하는 부분이다.
//            
//            // bufferedImage 를 이용한 파일 저장 -> 방식 1
//            BufferedImage bufferedImage=null;
//            bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);
//            File saveFile=new File(savePath+qrName);
//            ImageIO.write(bufferedImage, "png", saveFile);
//
//            // fileOutputStream 을 이용한 파일 저장 -> 방식 2
//            FileOutputStream fileOutputStream=new FileOutputStream(new File(savePath+qrName));
//            fileOutputStream.write(outputStream.toByteArray());
//            fileOutputStream.close();
//
//            }
//            // byteArray를 base64로 변환한 이유는 프론트에서 파일경로가 아닌 binary 형식으로 전송해서 보여주기 위해서다.
//            // 이렇게 할 경우 DB에 이미지를 저장하지 않고 화면에 보여줄 수 있다.
//        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
//    }
//	
//    private BitMatrix emptyQR(BitMatrix bitMatrix, int regionHeight, int regionWidth) {
//        // 이 메소드는 bitmatrix에 네모난 공간을 만드는 것이다.
//        
//        // 빈 공간의 넓이와 높이
//         int width=bitMatrix.getWidth();
//         int height=bitMatrix.getHeight();
//
//         // 빈 공간의 위치(중앙으로 설정했다.)
//         int left=(width-regionWidth)/2;
//         int top=(height-regionHeight)/2;
//
// 	// 빈 공간 생성하기(이때 색은 offColor)
// 	bitMatrix.setRegion(left,top,regionWidth,regionHeight);
// 	// 빈 공간의 색을 배경색으로 반전시킨다.(fixel 단위로 찾아서 색을 뒤집는다.)
//         for (int y = top; y <= top+regionHeight; y++) {
//             for (int x = left; x <= left+regionWidth; x++) {
//                 if(bitMatrix.get(x, y)){
//                     bitMatrix.unset(x,y);
//                 };
//             }
//         }
//         return bitMatrix;
//     }
}
