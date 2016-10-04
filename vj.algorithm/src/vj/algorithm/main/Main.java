package vj.algorithm.main;


public class Main {
	public static void main(String[] args) {
		
		/* Test 1
		import java.awt.image.BufferedImage;
		import vj.algorithm.io.ConstantPath;
		import vj.algorithm.io.ImageManager;
		import vj.algorithm.io.MatrixImage;


		String path = "images/Test/hao.png";
		BufferedImage buff = ImageManager.getBufferedImage(path);
		
		
		ImageManager.saveImageBuff(ImageManager.getGrayImage(buff)
				, ConstantPath.ImageTest + "\\out1.png");
		
		ImageManager.saveImageBuff(ImageManager.getGrayImageFormular(buff)
				, ConstantPath.ImageTest + "\\out2.png");
		
		
		ImageManager.saveBufferedImageToText(ImageManager.getGrayImageFormular(buff), ConstantPath.MatrixTest + "\\abc.txt");
		
		MatrixImage mt = MatrixImage.readFileTxtToBufferedImage(ConstantPath.MatrixTest + "\\abc.txt");
		ImageManager.saveImageBuff(ImageManager.convertMatrixImageToBufferedImage(mt), ConstantPath.ImageTest + "\\output2.png", "png");

		*/
		
		
		
		/* Test 2
		 * import java.util.ArrayList;
			import vj.algorithm.io.ConstantPath;
			import vj.algorithm.io.ImageManager;
			ArrayList<String> nameList = ImageManager.getListNameImage(ConstantPath.ImagePFACE);
		
		for(int i=0;i<nameList.size();i++){
			String temp = nameList.get(i);
			System.out.println(temp);
		}
		System.out.println(nameList.size());
		
		*/
		
		/* Test 3
			import java.awt.image.BufferedImage;
			
			import vj.algorithm.io.ConstantPath;
			import vj.algorithm.io.ImageManager;

		String path = "images/Test/hao.png";
		BufferedImage buff = ImageManager.getBufferedImage(path);
		
		int w = buff.getWidth();
		int h = buff.getHeight();
		double tile = .2;
		w = (int)(w * tile);
		h = (int)(h * tile);
		
		BufferedImage buffOut = ImageManager.scaleImage(buff, buff.getType(), w, h, 0.2, 0.2);
		ImageManager.saveImageBuff(buffOut, ConstantPath.ImageTest + "\\scale.png", "png");
		
		*/
		
		/* Test 4
		 	import java.util.ArrayList;

			import vj.algorithm.io.ConstantPath;
			import vj.algorithm.io.ImageManager;
			import vj.algorithm.io.MatrixImage;


		ArrayList<MatrixImage> arr = ImageManager.getListMatrixImage(ConstantPath.ImagePFACE, ConstantPath.MatrixPFACE);
		System.out.println(arr.size());
		*/
		
		/*
		import vj.algorithm.io.ConstantPath;
		import vj.algorithm.io.MatrixImage;

		MatrixImage mt = MatrixImage.readFileTxtToBufferedImage(ConstantPath.MatrixTest + "\\" + "abc.txt");
		MatrixImage integralImage = MatrixImage.getIntegralImage(mt);
		integralImage.print();
		
		*/

		System.out.println("OK");
	}
}
