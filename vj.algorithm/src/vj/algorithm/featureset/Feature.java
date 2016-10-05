package vj.algorithm.featureset;

import java.io.PrintWriter;
import java.sql.Savepoint;

import vj.algorithm.io.MatrixImage;

/*
 * 1. {FeatureType, x, y, w, h} --> each FeatureType has ArrayList<Feature> {type, x, y, w, h (w,h: 1 --> sizeSubWindow)}
 * 2. Save Feature thành txt
 * 3. Save ArrayList<Feature> save to file
 * 4. Gộp toàn bộ Feature của từng loại thành chung 1 ArrayList
 * 5. getFeatureValue(Matrix integralImage, Feature feature): lấy giá trị của từng Feature
 * 6. Với một MatrixIntegralImage: Save valueFeature + FeatureType + (x,y,w,h)
 * 7. Với FeatureType(file txt) --> tính valueFeature (công thức)
 * --> lưu thành 1 file txt
 */
public class Feature {
	String typeFeature;
	int x, y, w, h;
	MatrixImage matrixType;
	
	public Feature(String f, int x, int y, int w, int h, String pathMtType){
		this.typeFeature = f;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.matrixType = readMatrixType(pathMtType);
	}
	
	public Feature(String f, int x, int y, int w, int h, MatrixImage MtType){
		this.typeFeature = f;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.matrixType = MtType;
	}
	
	public Feature(String f, MatrixImage mt){
		this.typeFeature = f;
		this.matrixType = mt;
	}
	public Feature(String f, String pathMtType){
		this.typeFeature = f;
		this.matrixType = readMatrixType(pathMtType);
	}
	
	
	public static void writeFeature(Feature ft, String nameFileText){
		
		try {
			PrintWriter writer = new PrintWriter(nameFileText, "UTF-8");
			writer.println(ft.typeFeature);
			writer.println(ft.x);
			writer.println(ft.y);
			writer.println(ft.w);
			writer.println(ft.w);
			MatrixImage mtFeature = ft.matrixType;
			int rows = mtFeature.getRows();
			int cols = mtFeature.getCols();
			
			System.out.println(rows + " " + cols);
			for(int i=0;i<mtFeature.getSize();i++){
				writer.print(mtFeature.getData()[i] + " ");

				if((i + 1) % cols == 0 && i > 0){
					writer.println();
				}
			}

			writer.println();
			
			
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void createFeature(String pathRoot, String pathMtType, String pathMtImage){
		MatrixImage mtType = readMatrixType(pathMtType);
		MatrixImage mtImage = readMatrixType(pathMtImage);
		String typeofFeature = "type1";
		
		int hType = mtType.getRows();
		int wType = mtType.getCols();
		
		int hImage = mtImage.getRows();
		int wImage = mtImage.getCols();
		
		int hInit = hType;
		int wInit = wType;
		
		int x0 = 0;
		int y0 = 0;
		
		int count = 1;
		do {
			MatrixImage mt = new MatrixImage(hInit, wInit);
			
			// Đoạn code này cần xử lý lại (04/10/2016)
			double[][]temp = new double[hImage][wImage];
			int iRow = 0;
			int jCol = 0;
			
			for(int i=0;i<hImage * wImage;i++){
				temp[iRow][jCol] = mtImage.getData()[i];
				jCol++;
				if(jCol == wImage){
					jCol = 0;
					iRow++;
				}
			}
			int k = 0;
			for(int i=x0;i<x0 + hInit;i++){
				for(int j=y0;j<y0 + wInit;j++){
					mt.getData()[k++] = temp[i][j];
				}
			}
			// Chuyển đổi mảng một chiều thành ma trận (matrix)
			
			Feature ft = new Feature(typeofFeature, x0, y0, wInit, hInit, mt);
			writeFeature(ft, pathRoot + "\\" + count + "_file.txt");

			
			
			count++;
			if(count > 1) break;
			
		}while(true);
		
	}
	
	public static MatrixImage readMatrixType(String path){
		return MatrixImage.readFileTxtToBufferedImage(path);
	}
}
