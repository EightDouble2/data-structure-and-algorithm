package com.johnny.datastructure.tree;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author johnnyhao
 *
 * 哈夫曼编码
 */
public class HuffmanCoding {

    public static void main(String[] args) {

        String string = "i like like like java do you like a java";

        // 获取字符串的字节数组
        byte[] bytes = string.getBytes();
        System.out.printf("压缩前字节数组\n%s\n压缩前长度为%d\n", Arrays.toString(bytes), bytes.length);

        HuffmanCodingImpl huffmanCoding = new HuffmanCodingImpl();

        // 将字节数组构建为构建哈夫曼树
        HuffmanCodingNode huffmanCodingNode = huffmanCoding.buildHuffmanTree(bytes);

        // 前序遍历哈夫曼树
        System.out.println("前序遍历哈夫曼树");
        huffmanCoding.prefixOrder(huffmanCodingNode);

        // 生成哈夫曼编码表
        Map<Byte, String> huffmanCodingMap = huffmanCoding.toHuffmanCoding(huffmanCodingNode);
        System.out.printf("生成哈夫曼编码表\n%s\n", huffmanCodingMap);

        // 压缩
        byte[] huffmanCodingBytes = huffmanCoding.compress(bytes, huffmanCodingMap);
        System.out.printf("压缩后字节数组\n%s\n压缩后长度为%d\n", Arrays.toString(huffmanCodingBytes), bytes.length);

        // 封装哈夫曼编码压缩
        huffmanCodingBytes = huffmanCoding.huffmanCodingCompress(bytes);
        System.out.printf("封装后压缩后字节数组\n%s\n压缩后长度为%d\n", Arrays.toString(huffmanCodingBytes), bytes.length);

        // 哈夫曼编码解压缩
        bytes = huffmanCoding.decompress(huffmanCodingBytes, huffmanCodingMap);
        System.out.printf("解压缩后字节数组\n%s\n解压缩后长度为%d\n", Arrays.toString(bytes), bytes.length);

        // 封装哈夫曼编码解压缩
        bytes = huffmanCoding.huffmanDecompress(huffmanCodingBytes);
        System.out.printf("封装后解压缩后字节数组\n%s\n解压缩后长度为%d\n", Arrays.toString(bytes), bytes.length);

        // 还原字符串
        System.out.printf("还原后的字符串\n%s\n", new String(bytes));

        // 压缩文件
        String srcFile = "C:\\Users\\johnn\\Desktop\\1.jpg";
        String compressFile = "C:\\Users\\johnn\\Desktop\\1.zip";
        huffmanCoding.huffmanCodingCompressFile(srcFile, compressFile);
		System.out.println("压缩成功");

        // 解压缩文件
        String decompressFile = "C:\\Users\\johnn\\Desktop\\11.jpg";
        huffmanCoding.huffmanCodingDecompressFile(compressFile, decompressFile);
        System.out.println("解压成功");
    }
}

/**
 * 节点
 * 为了实现对节点的排序，实现Comparable接口，使用Collections集合排序
 */
@ToString(exclude = {"left", "right"})
@Getter
@Setter
@RequiredArgsConstructor
class HuffmanCodingNode implements Comparable<HuffmanCodingNode> {
    /**
     * 字节数据
     */
    private final Byte data;

    /**
     * 权重
     */
    @NonNull
    private Integer weight;

    /**
     * 左叶子节点
     */
    private HuffmanCodingNode left;

    /**
     * 右叶子节点
     */
    private HuffmanCodingNode right;

    /**
     * 实现compareTo方法，从小到大排列
     * @param huffmanCodingNode 节点
     * @return int
     */
    @Override
    public int compareTo(HuffmanCodingNode huffmanCodingNode) {
        return this.getWeight() - huffmanCodingNode.getWeight();
    }
}

/**
 * 哈夫曼编码
 */
class HuffmanCodingImpl {
    /**
     * 哈夫曼编码表
     */
    private final Map<Byte, String> huffmanCodingMap = new HashMap<>();

    /**
     * 构建哈夫曼树
     * @param bytes 字节数组
     * @return 哈夫曼树根节点
     */
    public HuffmanCodingNode buildHuffmanTree(byte[] bytes) {

        List<HuffmanCodingNode> huffmanCodingNodes = new ArrayList<>();

        // 利用Map统计每个字节出现次数
        Map<Byte, Integer> byteCountMap = new HashMap<>(16);

        for (byte aByte : bytes) {
            if (byteCountMap.containsKey(aByte)) {
                byteCountMap.put(aByte, byteCountMap.get(aByte) + 1);
            }
            else {
                byteCountMap.put(aByte, 1);
            }
        }

        // 初始化哈夫曼节点，并添加到集合中
        byteCountMap.forEach((key, value) -> huffmanCodingNodes.add(new HuffmanCodingNode(key, value)));

        // 循环处理到只剩最后一个节点，即根节点
        while (huffmanCodingNodes.size() > 1) {
            // 排序
            Collections.sort(huffmanCodingNodes);

            // 取出权值最小的两个节点
            HuffmanCodingNode left = huffmanCodingNodes.get(0);
            HuffmanCodingNode right = huffmanCodingNodes.get(1);

            // 生成父节点，构建成新的二叉树
            HuffmanCodingNode parent = new HuffmanCodingNode(null, left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);

            // 将节点从数组移除
            huffmanCodingNodes.remove(left);
            huffmanCodingNodes.remove(right);

            // 将新二叉树加入到集合中
            huffmanCodingNodes.add(parent);
        }

        // 返回根节点
        return huffmanCodingNodes.get(0);
    }

    /**
     * 前序遍历
     * 先输出父节点，再遍历左子树和右子树
     * @param huffmanCodingNode 节点
     */
    public void prefixOrder(HuffmanCodingNode huffmanCodingNode) {
        if (huffmanCodingNode != null) {
            System.out.println(huffmanCodingNode);
            prefixOrder(huffmanCodingNode.getLeft());
            prefixOrder(huffmanCodingNode.getRight());
        }
    }

    /**
     * 生成哈夫曼编码表
     * @param huffmanCodingNode 哈夫曼树根节点
     * @param code 路径，左路径为0，右路径为1
     * @param string 拼接路径
     */
    public void toHuffmanCoding(HuffmanCodingNode huffmanCodingNode, String code, String string) {
        // 拼接路径
        string += code;

        // 节点为空则不处理
        if(huffmanCodingNode != null) {
            // 节点数据为空则当前节点是非叶子节点
            if(huffmanCodingNode.getData() == null) {
                //向左递归
                toHuffmanCoding(huffmanCodingNode.getLeft(), "0", string);
                //向右递归
                toHuffmanCoding(huffmanCodingNode.getRight(), "1", string);
            }
            // 节点数据不为空则当前节点是叶子节点
            else {
                // 拼接路径则是哈夫曼编码
                huffmanCodingMap.put(huffmanCodingNode.getData(), string);
            }
        }
    }

    /**
     * 生成哈夫曼编码表默认调用方法
     * @param huffmanCodingNode 哈夫曼树根节点
     * @return 哈夫曼编码表
     */
    public Map<Byte, String> toHuffmanCoding(HuffmanCodingNode huffmanCodingNode) {
        // 哈夫曼树根节点为空则不处理
        if(huffmanCodingNode == null) {
            return null;
        }

        // 处理root的左子树
        toHuffmanCoding(huffmanCodingNode.getLeft(), "0", "");
        // 处理root的右子树
        toHuffmanCoding(huffmanCodingNode.getRight(), "1", "");

        // 返回哈夫曼编码表
        return huffmanCodingMap;
    }

    /**
     * 将字节数组压缩成哈夫曼编码字节数组
     * @param bytes 字节数组
     * @param huffmanCodingMap 哈夫曼编码表
     * @return 哈夫曼编码字节数组
     */
    public byte[] compress(byte[] bytes, Map<Byte, String> huffmanCodingMap) {
        // 利用哈夫曼编码表，将字节数组转换成对应的哈夫曼编码字符串
        StringBuilder huffmanCodings = new StringBuilder();
        for (byte aByte : bytes) {
            huffmanCodings.append(huffmanCodingMap.get(aByte));
        }

        System.out.printf("生成哈夫曼编码字符串\n%s\n", huffmanCodings);

        // 初始化压缩后的字节数组，计算长度可以用(stringBuilder.length() + 7) / 8
        byte[] huffmanCodeBytes = new byte[huffmanCodings.length() % 8 == 0 ? huffmanCodings.length() / 8 : huffmanCodings.length() / 8 + 1];

        // 因为是每八位对应一个字节，所以步长8
        for (int i = 0; i < huffmanCodings.length(); i += 8) {
            // 将二进制哈夫曼编码字符串转换成哈夫曼编码字节数组
            huffmanCodeBytes[i / 8] = Integer.valueOf(huffmanCodings.length() < (i + 8) ? huffmanCodings.substring(i) : huffmanCodings.substring(i, i + 8), 2).byteValue();
        }

        return huffmanCodeBytes;
    }

    /**
     * 封装哈夫曼编码压缩
     * @param bytes 压缩前字节数组
     * @return 压缩后字节数组
     */
    public byte[] huffmanCodingCompress(byte[] bytes) {
        // 将字节数组构建为构建哈夫曼树
        HuffmanCodingNode huffmanCodingNode = buildHuffmanTree(bytes);

        // 生成哈夫曼编码表
        toHuffmanCoding(huffmanCodingNode);

        // 压缩
        return compress(bytes, huffmanCodingMap);
    }

    /**
     * 将哈夫曼编码字节数组解压缩成字节数组
     * @param huffmanCodingBytes 哈夫曼编码字节数组
     * @param huffmanCodingMap 哈夫曼编码表
     * @return 字节数组
     */
    public byte[] decompress(byte[] huffmanCodingBytes, Map<Byte, String> huffmanCodingMap) {
        // 利用哈夫曼编码表，将字节数组转换成对应的哈夫曼编码字符串
        StringBuilder huffmanCodings = new StringBuilder();

        for (int i = 0; i < huffmanCodingBytes.length; i++) {
            // 将字节码转换成整型
            int b = huffmanCodingBytes[i];
            // 判断是不是最后一个字节
            if (i != huffmanCodingBytes.length - 1) {
                // 正数需要补高位
                b |= 256;
                // 生成对应的二进制补码
                String str = Integer.toBinaryString(b);
                // 截取最后八位
                huffmanCodings.append(str.substring(str.length() - 8));
            }
            // 最后一位不需要截取
            else {
                huffmanCodings.append(Integer.toBinaryString(b));
            }
        }

        System.out.printf("还原哈夫曼编码字符串\n%s\n", huffmanCodings);

        // 生成反向哈夫曼编码表
        Map<String, Byte> reverseHuffmanCodingMap = new HashMap<>(16);
        huffmanCodingMap.forEach((key, value) -> reverseHuffmanCodingMap.put(value, key));

        // 创建集合存放解压缩后的字节
        ArrayList<Byte> byteList = new ArrayList<>();
        // 循环遍历哈夫曼编码字符串
        int index = 0;
        while (index < huffmanCodings.length()) {
            // 匹配字节计数器
            int count = 0;
            // 匹配到的字节
            Byte aByte;

            while (true) {
                // 截取哈夫曼编码
                String key = huffmanCodings.substring(index, index + count);
                // 取出对应字节
                aByte = reverseHuffmanCodingMap.get(key);

                // 未取出继续遍历
                if (aByte == null) {
                    count++;
                }
                // 取出则跳出循环
                else {
                    break;
                }
            }

            // 将取出的字节加入集合
            byteList.add(aByte);

            // 指针后移到未遍历的编码
            index += count;
        }

        // 将集合还原成字节数组
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = byteList.get(i);
        }
        return bytes;
    }

    /**
     * 封装哈夫曼编码字节数组解压缩
     * @param huffmanCodingBytes 哈夫曼编码字节数组
     * @return 字节数组
     */
    public byte[] huffmanDecompress(byte[] huffmanCodingBytes) {
        return decompress(huffmanCodingBytes, huffmanCodingMap);
    }

    /**
     * 哈夫曼编码压缩文件
     * @param srcFile 原文件路径
     * @param compressFile 压缩文件路径
     */
    public void huffmanCodingCompressFile(String srcFile, String compressFile) {
        // 定义一个输出流
        OutputStream os = null;
        // 定义一个对象输出流
        ObjectOutputStream oos = null;
        // 定义一个文件的输入流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]
            byte[] bytes = new byte[is.available()];
            //读取文件
            is.read(bytes);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanCodingCompress(bytes);
            //创建文件的输出流, 存放压缩文件
            os = new FileOutputStream(compressFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把 哈夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //这里我们以对象流的方式写入哈夫曼编码，是为了以后我们恢复源文件时使用
            //注意一定要把哈夫曼编码写入压缩文件
            oos.writeObject(huffmanCodingMap);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (os != null) {
                    os.close();
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 哈夫曼编码解压缩文件
     * @param compressFile 压缩文件路径
     * @param decompressFile 压缩文件路径
     */
    public void huffmanCodingDecompressFile(String compressFile, String decompressFile) {
        // 定义文件输入流
        InputStream is = null;
        // 定义一个对象输入流
        ObjectInputStream ois = null;
        // 定义文件的输出流
        OutputStream os = null;
        try {
            // 创建文件输入流
            is = new FileInputStream(compressFile);
            // 创建一个和文件输入流关联的对象输入流
            ois = new ObjectInputStream(is);
            // 读取byte数组  huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取哈夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            //解码
            byte[] bytes = decompress(huffmanBytes, huffmanCodes);
            //将bytes 数组写入到目标文件
            os = new FileOutputStream(decompressFile);
            //写数据到 dstFile 文件
            os.write(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}