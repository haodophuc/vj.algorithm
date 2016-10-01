package vj.algorithm.featureset;
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

}
