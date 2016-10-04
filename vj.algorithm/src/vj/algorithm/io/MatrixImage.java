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
