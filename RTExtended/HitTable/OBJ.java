package RTExtended.HitTable;

import RTExtended.Material.Material;
import RTExtended.Point;
import RTExtended.Vec2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OBJ {
    public static Mesh3DModel   loadToMeshModel(String file_path, Material mat)
    {

        Mesh3DModel model = new Mesh3DModel();

        try (InputStream fileData = new FileInputStream(file_path);
             InputStreamReader InputStreamReader = new InputStreamReader(fileData);
             BufferedReader buffer = new BufferedReader(InputStreamReader))
        {
            String line;
            String[] text;
            int[] index = new int[3];

            Point[] vertex;
            Vec2[] uv_data;
            Triangle triangle;

            ArrayList<Double> vertexData = new ArrayList<>();
            ArrayList<Double> uData = new ArrayList<>();
            ArrayList<Double> vData = new ArrayList<>();
            while((line=buffer.readLine())!=null)
            {
                text = line.split(" "); //用空格分割数据，例如"v 1 2 "会被分割为"v","1","2"
                switch (text[0].trim()) {
                    case "v" -> {
                        //如果第一个元素是顶点
                        vertexData.add(Double.parseDouble(text[1])); //转换字符为顶点
                        vertexData.add(Double.parseDouble(text[2]));
                        vertexData.add(Double.parseDouble(text[3]));
                    }
                    case "vt" -> {
                        //如果第一个数是uv
                        uData.add(Double.parseDouble(text[1]));
                        vData.add(Double.parseDouble(text[2]));
                    }
                    case "f" -> {
                        //如果第一个数是三角形面
                        String[] info1, info2, info3;
                        if (text[1].contains("/")) {
                            info1 = text[1].split("/"); // 示例：f 5/1/1 3/2/1 1/3/1，‘/’号用来划分索引信息，对应信息为：“顶点索引/纹理索引/法线索引”
                            info2 = text[2].split("/");
                            info3 = text[3].split("/");

                            vertex = new Point[3];
                            index[0] = Integer.parseInt(info1[0]) - 1;
                            vertex[0] = new Point(vertexData.get(index[0] * 3),
                                    vertexData.get(index[0] * 3 + 1),
                                    vertexData.get(index[0] * 3 + 2));
                            index[1] = Integer.parseInt(info2[0]) - 1;
                            vertex[1] = new Point(vertexData.get(index[1] * 3),
                                    vertexData.get(index[1] * 3 + 1),
                                    vertexData.get(index[1] * 3 + 2));
                            index[2] = Integer.parseInt(info3[0]) - 1;
                            vertex[2] = new Point(vertexData.get(index[2] * 3),
                                    vertexData.get(index[2] * 3 + 1),
                                    vertexData.get(index[2] * 3 + 2));

                            uv_data = new Vec2[3];
                            index[0] = Integer.parseInt(info1[1]) - 1;
                            uv_data[0] = new Vec2(uData.get(index[0]), vData.get(index[0]));
                            index[1] = Integer.parseInt(info2[1]) - 1;
                            uv_data[1] = new Vec2(uData.get(index[1]), vData.get(index[1]));
                            index[2] = Integer.parseInt(info3[1]) - 1;
                            uv_data[2] = new Vec2(uData.get(index[2]), vData.get(index[2]));

                            triangle = new Triangle(vertex, uv_data, mat);
                            model.add(triangle);
                        }else {
                            vertex = new Point[3];
                            index[0] = Integer.parseInt(text[1]) - 1;
                            vertex[0] = new Point(vertexData.get(index[0] * 3),
                                                  vertexData.get(index[0] * 3 + 1),
                                                  vertexData.get(index[0] * 3 + 2));
                            index[1] = Integer.parseInt(text[2]) - 1;
                            vertex[1] = new Point(vertexData.get(index[1] * 3),
                                                  vertexData.get(index[1] * 3 + 1),
                                                  vertexData.get(index[1] * 3 + 2));
                            index[2] = Integer.parseInt(text[3]) - 1;
                            vertex[2] = new Point(vertexData.get(index[2] * 3),
                                                  vertexData.get(index[2] * 3 + 1),
                                                  vertexData.get(index[2] * 3 + 2));

                            triangle = new Triangle(vertex, mat);
                            model.add(triangle);
                        }
                    }
                }
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        return model;
    }

}
