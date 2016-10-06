package vj.algorithm.featureset;

import java.io.PrintWriter;

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
	
	public Feature(String f, int x, int y, int h, int w, String pathMtType){
		this.typeFeature = f;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.matrixType = readMatrixType(pathMtType);
	}
	
	public Feature(String f, int x, int y, int h, int w, MatrixImage MtType){
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
			writer.println(ft.h);
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
		String typeofFeature = "type3";
		
		int hType = mtType.getRows();
		int wType = mtType.getCols();

		int hImage = mtImage.getRows();
		int wImage = mtImage.getCols();
		
		int hInit = hType;
		int wInit = wType;
		

		double [][]temp = MatrixImage.convertToMatrix(mtImage);
		
		for(int x0=0;x0<=hImage-hType;x0++){
			for(int y0=0;y0<=wImage-wType;y0++){
				
				for(int i1=hInit;i1<=hImage;i1++){
					for(int j1=wInit;j1<=wImage;j1++){
						
						if(x0 + i1 <= hImage && y0 + j1 <= wImage){
							
							MatrixImage mt = new MatrixImage(i1, j1, x0, y0, temp);
							Feature ft = new Feature(typeofFeature, x0, y0, i1, j1, mt);
							writeFeature(ft, pathRoot + "\\toado(" + x0 + "," + y0 + ")_" + (i1 + "_" + j1) + "_file.txt");
							
						}
						
						
					}
				}
				
			}
		}

		
	}
	
	public static MatrixImage readMatrixType(String path){
		return MatrixImage.readFileTxtToBufferedImage(path);
	}
}
