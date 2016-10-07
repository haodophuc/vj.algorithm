package vj.algorithm.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/*
 * 1. Save matrixImage (gray) {row, col, dat_Des1}
 * 2. Save dataMatrix file txt (row, col, dat_Des1)
 * 3. Read file txt --> MatrixImage
 * 4. Get Matrix Image Integral from MatrixImage
 */
public class MatrixImage {
	int rows, cols;
	double[]data;
	
	public double[] getData(){
		return this.data;
	}
	public int getRows(){
		return this.rows;
	}
	
	public MatrixImage(int i1, int j1, int x0, int y0, double[][]temp){
		int k = 0;
		double []dat = new double[temp.length * temp[0].length];
		
		for(int i=x0;i<x0 + i1;i++){
			for(int j=y0;j<y0 + j1;j++){
				dat[k++] = temp[i][j];
			}
		}
		this.rows = i1; this.cols = j1;
		this.data = dat;
	}
	public MatrixImage(double[][]temp){
		int k = 0;
		double []dat = new double[temp.length * temp[0].length];
		
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[0].length;j++){
				dat[k++] = temp[i][j];
			}
		}
		this.rows = temp.length;
		this.cols = temp[0].length;
		
		this.data = dat;
	}
	
	public static double[][] convertToMatrix(MatrixImage mtImage){
		int hImage = mtImage.getRows();
		int wImage = mtImage.getCols();
		
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
		
		return temp;
	}
	public int getCols(){
		return this.cols;
	}
	public MatrixImage(int rows, int cols, double []data){
		this.rows = rows;
		this.cols = cols;
		this.data = data;
	}
	
	public MatrixImage(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows * cols];
	}
	
	public double getValue(int i, int j){
		int idx = (i) * cols + j % cols;
		return data[idx];
	}
	public void setValue(int i, int j, double v){
		int idx = (i) * cols + j % cols;
		data[idx] = v;
	}
	
	public static MatrixImage getIntegralImage(MatrixImage mt){
		int r = mt.rows;
		int c = mt.cols;
		MatrixImage integralImage = new MatrixImage(r, c);
		
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				double value = mt.getValue(i, j);
            	value += i > 0 ? integralImage.getValue(i - 1, j) : 0;
            	value += j > 0 ? integralImage.getValue(i, j - 1) : 0;
            	value -= i > 0 && j > 0 ? integralImage.getValue(i - 1, j - 1) : 0;
            	
            	integralImage.setValue(i, j, value);
			}
		}
		return integralImage;
	}
	
	public static MatrixImage getCandidates(double [][]temp, MatrixImage type, double[][]temp_0){
		int hType = type.getRows();
		int wType = type.getCols();
		
		int hImage = temp.length;
		int wImage = temp[0].length;
		
		int []arrayRows = new int [hType];
		int []arrayCols = new int [wType];
		
		int divRows = (int)(hImage / hType);
		int countRows = 0;
		for(int i=0;i<hType - 1;i++){
			arrayRows[i] = divRows;
			countRows += divRows;
		}
		arrayRows[hType - 1] = hImage - countRows;
		
		
		int divCols = (int)(wImage / wType);
		int countCols = 0;
		for(int i=0;i<wType - 1;i++){
			arrayCols[i] = divCols;
			countCols += divCols;
		}
		arrayCols[wType - 1] = wImage - countCols;
		
		ObjectCandidate[] res = new ObjectCandidate[arrayRows.length * arrayCols.length];
		int k = 0;
		
		int x0 = 0;
		int y0 = 0;
		
		for(int i=0;i<arrayRows.length;i++){
			for(int j=0;j<arrayCols.length;j++){
				res[k++] = new ObjectCandidate(x0, y0, arrayRows[i], arrayCols[j]);
				
				y0 += arrayCols[j];
			}
			x0 += arrayRows[i];
			y0 = 0;
		}


		k = 0;
		double []kq = new double[res.length];
		for(int i=0;i<res.length;i++){
			kq[k++] = getValueRegion(res[i], temp_0);
		}
		
		return new MatrixImage(kq, hType, wType);
	}
	
	public static double getValueFeature(MatrixImage mt, MatrixImage mtType){
		double res = 0;
		int r = mt.getRows();
		int c = mt.getCols();
		
		double [][]temp = convertToMatrix(mt);
		double [][]temp1 = convertToMatrix(mtType);
		
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				if(temp1[i][j] == 1){
					res += temp[i][j];
				}
				else {
					res -= temp[i][j];
				}
			}
		}
		
		return res;
	}
	
	public MatrixImage(double []temp, int r, int c){
		this.rows = r;
		this.cols = c;
		this.data = temp;
	}
	
	public static double getValueRegion(ObjectCandidate obj, double[][]temp){
		int x0 = obj.x0;
		int y0 = obj.y0;
		int w = obj.w;
		int h = obj.h;

		
		return temp[x0 + h][y0 + w] + temp[x0][y0] - temp[x0 + h][y0] - temp[x0][y0 + w];
	}
	
	public static void printStaticArray(double [][]temp){

		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[0].length;j++){
				System.out.print(temp[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printArray(){
		double[][]temp = convertToMatrix(this);
		for(int i=0;i<temp.length;i++){
			for(int j=0;j<temp[0].length;j++){
				System.out.print(temp[i][j] + " ");
			}
			System.out.println();
		}
	}
	public void print(){
		System.out.println(rows + " " + cols);
		for(int i=0;i<getSize();i++){
			System.out.print(data[i] + " ");
			if((i + 1) % cols == 0 && i > 0){
				System.out.println();
			}
		}
		System.out.println();
	}
	public int getSize(){
		return rows * cols;
	}
	
	
	public static double[][] f(MatrixImage mt){
		int r = mt.getRows();
		int c = mt.getCols();
		
		double[][]temp = new double[r+1][c+1];
		for(int j=0;j<=c;j++){
			temp[0][j] = 0;
		}
		for(int i=0;i<=r;i++){
			temp[i][0] = 0;
		}
		
		double[][]temp1 = convertToMatrix(mt);
		for(int i=1;i<=r;i++){
			for(int j=1;j<=c;j++){
				temp[i][j] = temp1[i-1][j-1];
			}
		}
		return temp;
	}
	
	public static double[][] AddRow0AndCol0(MatrixImage mt){
		int r = mt.getRows();
		int c = mt.getCols();
		
		double[][]temp = new double[r+1][c+1];
		for(int j=0;j<=c;j++){
			temp[0][j] = 0;
		}
		for(int i=0;i<=r;i++){
			temp[i][0] = 0;
		}
		
		double[][]temp1 = convertToMatrix(mt);
		for(int i=1;i<=r;i++){
			for(int j=1;j<=c;j++){
				temp[i][j] = temp1[i-1][j-1];
			}
		}
		return temp;
	}
	public static void writeFileTxtBufferedImage(MatrixImage mt, String nameFileText){
		int h = mt.rows;
		int w = mt.cols;
		
		try {
			PrintWriter writer = new PrintWriter(nameFileText, "UTF-8");
			writer.println(h + " " + w);
			
			int k = 0;
			for(int x=0;x<h;x++){
				for(int y=0;y<w;y++){

					int gray = (int)mt.data[k++];
					writer.print(gray + " ");
				}
				writer.println();
			}
			
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static MatrixImage readFileTxtToBufferedImage(String fileText){
		int h, w;
		double[]data;
		int k = 0;
		MatrixImage result = null;
		
		BufferedReader br = null;
		try {
			FileReader fileReader = new FileReader(new File(fileText));			
			br = new BufferedReader(fileReader);
			
			String line = null;
			line = br.readLine();
			String[]lines = line.split(" ");
			
			h = Integer.parseInt(lines[0]);
			w = Integer.parseInt(lines[1]);
			data = new double[h * w];
			
			while((line = br.readLine()) != null){
				lines = line.split(" ");
				for(int j=0;j<lines.length;j++){
					data[k++] = Double.parseDouble(lines[j]);
				}
			}
			
			result = new MatrixImage(h, w, data);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
}

class ObjectCandidate{
	int x0, y0, w, h;
	public ObjectCandidate(int x0, int y0, int h, int w){
		this.x0 = x0;
		this.y0 = y0;
		this.w = w;
		this.h= h;
	}
	public void print(){
		System.out.println(x0 + "_ " + y0 + "_" + w + "_" + h);
	}
}