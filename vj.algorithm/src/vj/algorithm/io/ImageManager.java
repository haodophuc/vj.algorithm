package vj.algorithm.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * 1. Đọc file path image --> ArrayList<MatrixImage>
 * 2. Đọc file image --> MatrixImage
 * 3. Chuyển từ matrixImage --> images (save folder draft)
 * 4. Đọc ảnh lớn --> (w,h * 0.8) --> save images new
 * 5. Convert ảnh màu sang ảnh xám
 */
public class ImageManager {
	
	
	
	public static ArrayList<String> getListNameImage(String path){
		File folder = new File(path);
		File[] files = folder.listFiles();
		
		ArrayList<String> namesList = new ArrayList<>();
		for(File f : files){
			if(f.isFile() && 
					(		f.getName().endsWith(".jpg") ||
							f.getName().endsWith(".jpeg") ||
							f.getName().endsWith(".gif") ||
							f.getName().endsWith(".bmp") ||
							f.getName().endsWith(".tif") ||
							f.getName().endsWith(".png")
					) 	
			){
				namesList.add(f.getName());
			}
		}
		
		return namesList;
	}
	
	public static BufferedImage getBufferedImage(String image){
		File file = new File(image);
		BufferedImage inData = null;
		try {
			inData = ImageIO.read(file);
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		return inData;
	}
	

	public static BufferedImage convertMatrixImageToBufferedImage(MatrixImage mt){
		int h = mt.rows;
		int w = mt.cols;
		int size = mt.getSize();
		
		BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		for(int i=0;i<size;i++){
			int x = i/w;
			int y = i % w;
			int gray = (int)mt.data[i];
			Color newColor = new Color(gray, gray, gray);
            buff.setRGB(y, x, newColor.getRGB());
		}
		return buff;
	}
	
	
	public static BufferedImage getGrayImage(BufferedImage buff){
		int w = buff.getWidth();
		int h = buff.getHeight();

		// call h before w (h: rows, w: cols), x theo h và y theo w
		// khi gọi hàm getRGB hoặc setRGB thì đảo ngược thứ tự y,x
		for(int x=0;x<h;x++){
			for(int y=0;y<w;y++){
				int p = buff.getRGB(y, x);
				
				int a = (p >> 24) & 0xff;
				int r = (p >> 16) & 0xff;
				int g = (p >> 8) & 0xff;
				int b = p & 0xff;
				
				int avg = (r + g + b)/3;
				p = (a << 24) | (avg << 16) | (avg << 8) | avg;
				
				buff.setRGB(y, x, p);
			}
		}
		
		return buff;
	}
	
	public static MatrixImage saveBufferedImagetoMatrixImage(BufferedImage buffGray){
		int h = buffGray.getHeight();
		int w = buffGray.getWidth();
		double []data = new double[h * w];
		int k = 0;
		try {	
			for(int x=0;x<h;x++){
				for(int y=0;y<w;y++){

					Color c = new Color(buffGray.getRGB(y, x));
		               
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() * 0.114);

	               int gray = (red + green + blue);
	               data[k++] = gray;
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new MatrixImage(h, w, data);
	}
	
	public static void getIndexArray(int n, int[]train, int[]test){
		Random r = new Random();
		
		ArrayList<Integer>arr = new ArrayList<>();
		int count = 0;
		Object[] arrTemp = new Object[train.length];
		
		do {
			int temp = r.nextInt(n) % n;
			if(!arr.contains(temp)){
				arr.add(temp);
				count++;
			}
		}while(count < arrTemp.length);
		
		arrTemp = arr.toArray();
		
		for(int i=0;i<arrTemp.length;i++){
			int temp = (int)arrTemp[i];
			train[i] = temp;
		}
		
		System.out.println(train.length + " " + test.length);
		int k = 0;
		for(int i=0;i<n;i++){
			boolean ok = true;
			
			for(int j=0;j<train.length;j++){
				if(i == train[j]){
					ok = false;
				}
			}
			if(ok){
				test[k++] = i;
			}
		}
		
		
	}
	public static ArrayList<MatrixImage> getListMatrixImage(String pathImages, String pathMatrix){
		ArrayList<String> arrNameListImage = getListNameImage(pathImages);
		
		ArrayList<MatrixImage> result = new ArrayList<>();
		for(int i=0;i<arrNameListImage.size();i++){
			String name = arrNameListImage.get(i);
			
			BufferedImage grayImage = getGrayImage(getBufferedImage(pathImages + "\\" + name));
			MatrixImage mt = saveBufferedImagetoMatrixImage(grayImage);
			result.add(mt);

			saveBufferedImageToText(grayImage, pathMatrix + "\\" + name + ".txt");
		}
		return result;
	}
	
	public static void saveBufferedImageToText(BufferedImage buffGray, String nameFileText){
		int h = buffGray.getHeight();
		int w = buffGray.getWidth();
		try {
			PrintWriter writer = new PrintWriter(nameFileText, "UTF-8");
			writer.println(h + " " + w);
			
			for(int x=0;x<h;x++){
				for(int y=0;y<w;y++){

					Color c = new Color(buffGray.getRGB(y, x));
		               
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() * 0.114);

	               int gray = (red + green + blue);
	               
					writer.print(gray + " ");
				}
				writer.println();
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		

	}
	
	public static BufferedImage scaleImage(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.drawRenderedImage(sbi, at);
	    }
	    return dbi;
	}

	
	public static BufferedImage getGrayImageFormular(BufferedImage buff){
		int w = buff.getWidth();
		int h = buff.getHeight();

		// call h before w (h: rows, w: cols), x theo h và y theo w
		// khi gọi hàm getRGB hoặc setRGB thì đảo ngược thứ tự y,x
		for(int x=0;x<h;x++){
			for(int y=0;y<w;y++){
	               Color c = new Color(buff.getRGB(y, x));
	               
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() * 0.114);
	               
	               System.out.println(red + " " + green + " " + blue);
	               
	               int gray = (red + green + blue);
	               System.out.println(gray);
	               Color newColor = new Color(gray, gray, gray);
	               buff.setRGB(y, x, newColor.getRGB());
			}
		}
		
		return buff;
	}
	
	public static void saveImageBuff(BufferedImage buff, String path, String type){
		try {
			File f = new File(path);
			ImageIO.write(buff, type, f);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
