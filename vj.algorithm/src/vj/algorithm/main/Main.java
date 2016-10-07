package vj.algorithm.main;

import vj.algorithm.featureset.Feature;
import vj.algorithm.io.ConstantPath;
import vj.algorithm.io.ImageManager;




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
		/* Test 5
		String path = ConstantPath.FeatureTest;
		String pathDraft = ConstantPath.FeatureDraft;
				
		import vj.algorithm.featureset.Feature;
		import vj.algorithm.io.ConstantPath;
		
		Feature.createFeature(pathDraft, path + "\\type1.txt", path + "\\matrix.txt");
		*/


		
		/*
		 * 
		 * 
		import java.util.ArrayList;
		import java.util.Random;
		
		import vj.algorithm.io.ImageManager;

		int n = 15;
		int []train = new int[n/2];
		int []test = new int[n - train.length];
		
		ImageManager.getIndexArray(n, train, test);
		for(int i=0;i<train.length;i++){
			System.out.print(train[i] + " ");
		}
		System.out.println();
		for(int i=0;i<test.length;i++){
			System.out.print(test[i] + " ");
		}
		System.out.println();
		*/

		//ImageManager.saveListMatrixImage(ConstantPath.ImagePFACE, ConstantPath.MatrixPFACETRAIN, ConstantPath.MatrixPFACETEST);
		//ImageManager.saveListMatrixImage(ConstantPath.ImageNFACE, ConstantPath.MatrixNFACETRAIN, ConstantPath.MatrixNFACETEST);
		
		
		/*
		String path = ConstantPath.FeatureTest;
		String pathDraft = ConstantPath.FeatureDraftNFACES;
				
		Feature.createListFeature(ConstantPath.MatrixNFACETRAIN, path + "\\type3.txt", pathDraft);
		*/
		
		String path = ConstantPath.FeatureTest;
		String pathDraft = ConstantPath.FeatureDraft;
		int n = 4;
		for(int i=1;i<=n;i++){
			Feature.createListFeature(ConstantPath.MatrixNFACETRAIN, path + "\\type" + i + ".txt", pathDraft, false);
			Feature.createListFeature(ConstantPath.MatrixPFACETRAIN, path + "\\type" + i + ".txt", pathDraft, true);
				
		}
		
		//ImageManager.deleteFile(ConstantPath.FeatureDraftNFACES + "\\" + "type3");
		
		
		/*
				 
		import vj.algorithm.io.ConstantPath;
		import vj.algorithm.io.MatrixImage;
		MatrixImage mt = MatrixImage.readFileTxtToBufferedImage(ConstantPath.MatrixTest + "\\" + "abc.txt");
		
		MatrixImage integralImage = MatrixImage.getIntegralImage(mt);
		integralImage.print();
		System.out.println("---------------");
		double[][]temp = MatrixImage.f(integralImage);
		MatrixImage.printStaticArray(temp);
		
		
		double[][]getCand = MatrixImage.convertToMatrix(integralImage);
		MatrixImage mtType = MatrixImage.readFileTxtToBufferedImage(ConstantPath.FeatureTest + "\\" + "type1.txt");
		
		MatrixImage result = MatrixImage.getCandidates(getCand, mtType, temp);
		result.print();
		
		double res = MatrixImage.getValueFeature(result, mtType);
		System.out.println(res);
		
		*/
		System.out.println("OK");
	}
}
