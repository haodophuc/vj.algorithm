package vj.algorithm.featureset;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import vj.algorithm.io.ImageManager;
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
	
	
	public static void writeFeature(Feature ft, String nameFileText, MatrixImage result, double res){
		
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
			
			for(int i=0;i<result.getSize();i++){
				writer.print(result.getData()[i] + " ");

				if((i + 1) % result.getCols() == 0 && i > 0){
					writer.println();
				}
			}
			
			writer.println(res);
			
			
			
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createListFeature(String pathImageTrain, String pathType, String pathDraft, boolean test){
		ArrayList<String> arrNameListImage = ImageManager.getListNameTxt(pathImageTrain);
		System.out.println(arrNameListImage.size());
		
		String[]spl = pathType.split("\\\\");
		String[] spl1 = spl[spl.length - 1].split(".txt");
		String nameType = spl1[0];
		String nameFile = "";
		if(test){
			nameFile += nameType + "_" + "pos.txt";
		}
		else {
			nameFile += nameType + "_" + "neg.txt";
		}
		
		try {
			PrintWriter writer = new PrintWriter(new File(pathDraft + "\\" + nameFile), "UTF-8");

			for(int i=0;i<arrNameListImage.size();i++){
				String path = pathImageTrain + "\\" + arrNameListImage.get(i);
				ArrayList<ObjectSaveValueFeature> arrSaveValueFeature =  Feature.createFeature(pathDraft, pathType, path, arrNameListImage.get(i));
				
				for(ObjectSaveValueFeature obj : arrSaveValueFeature){
					writer.println(obj.toString());
				}
				
			}
			writer.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		System.out.println("Done function createListFeature");
	}
	
	public static ArrayList<ObjectSaveValueFeature> createFeature(String pathRoot, String pathMtType, String pathMtImage, String nameImage){
		MatrixImage mtType = readMatrixType(pathMtType);
		MatrixImage mtImage = readMatrixType(pathMtImage);
		
		String[]spl = pathMtType.split("\\\\");
		String[] spl1 = spl[spl.length - 1].split(".txt");
		
		String[]_spl = pathMtImage.split("\\\\");
		String[] _spl1 = _spl[_spl.length - 1].split(".txt");
		
		System.out.println(_spl1[0]);
		
		String typeofFeature = spl1[0];
		pathRoot = pathRoot + "\\" + typeofFeature;

		/*
		File file = new File(pathRoot);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
		*/
		
		int hType = mtType.getRows();
		int wType = mtType.getCols();

		int hImage = mtImage.getRows();
		int wImage = mtImage.getCols();
		
		int hInit = hType;
		int wInit = wType;
		ArrayList<ObjectSaveValueFeature> arrSaveValueFeature = new ArrayList<>();

		double [][]temp = MatrixImage.convertToMatrix(mtImage);
		
		for(int x0=0;x0<=hImage-hType;x0++){
			for(int y0=0;y0<=wImage-wType;y0++){
				
				for(int i1=hInit;i1<=hImage;i1++){
					for(int j1=wInit;j1<=wImage;j1++){
						
						if(x0 + i1 <= hImage && y0 + j1 <= wImage){
							MatrixImage mt = new MatrixImage(i1, j1, x0, y0, temp);
							Feature ft = new Feature(typeofFeature, x0, y0, i1, j1, mt);
							
							MatrixImage integralImage = MatrixImage.getIntegralImage(mt);
							double[][]mang2Chieu = MatrixImage.AddRow0AndCol0(integralImage);
							double[][]getCand = MatrixImage.convertToMatrix(integralImage);
							
							MatrixImage result = MatrixImage.getCandidates(getCand, mtType, mang2Chieu);
							double res = MatrixImage.getValueFeature(result, mtType);
							
							
							//writeFeature(ft, pathRoot + "\\" + _spl1[0] + "_toado(" + x0 + "," + y0 + ")_" + (i1 + "_" + j1) + "_file.txt", result, res);
							ObjectSaveValueFeature obj = new ObjectSaveValueFeature(spl1[0], x0, y0, i1, j1, res, nameImage);
							arrSaveValueFeature.add(obj);
						}
						
					}
				}
				
			}
		}

		return arrSaveValueFeature;
	}
	
	public static MatrixImage readMatrixType(String path){
		return MatrixImage.readFileTxtToBufferedImage(path);
	}
}

class ObjectSaveValueFeature{
	String type;
	int x0, y0, w, h;
	double res;
	String nameImage;
	
	public ObjectSaveValueFeature(String type, int x0, int y0, int h, int w, double r, String name){
		this.type = type;
		this.x0 = x0;
		this.y0 = y0;
		this.h= h;
		this.w = w;
		this.res = r;
		this.nameImage = name;
	}
	@Override
	public String toString(){
		return type + "!" + x0 + "!" + y0 + "!" + h + "!" + w + "!" + res + "!" + nameImage;
	}
}