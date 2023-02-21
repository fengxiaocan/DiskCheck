package com.n.disk.common;


import java.awt.*;

/**
 * Created by MQ on 2017/6/16.
 */
public class MathUtil {


    /**
     * 获得两点之间的直线距离
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 两点之间的直线距离 two point distance
     */
    public static float getTwoPointDistance(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    /**
     * 根据两个点(x1,y1)(x2,y2)的坐标算出斜率
     *
     * @param x1 x1
     * @param x2 x2
     * @param y1 y1
     * @param y2 y2
     * @return 斜率 line slope
     */
    public static Float getLineSlope(float x1, float x2, float y1, float y2) {
        if (x2 - x1 == 0) {
            return null;
        }
        return (y2 - y1) / (x2 - x1);
    }

    /**
     * 根据传入的两点得到斜率
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 返回斜率 line slope
     */
    public static Float getLineSlope(Point p1, Point p2) {
        if (p2.x - p1.x == 0) {
            return null;
        }
        return (p2.y - p1.y) * 1f / (p2.x - p1.x);
    }

    /**
     * Get middle point between p1 and p2.
     * 获得两点连线的中点
     *
     * @param p1 PointF
     * @param p2 PointF
     * @return 中点 middle point
     */
    public static Point getMiddlePoint(Point p1, Point p2) {
        return new Point((int) ((p1.x + p2.x) / 2.0f), (int) ((p1.y + p2.y) / 2.0f));
    }


    /**
     * Get the point of intersection between circle and line.
     * 获取 通过指定圆心，斜率为lineK的直线与圆的交点。
     *
     * @param pMiddle The circle center point.
     * @param radius  The circle radius.
     * @param lineK   The slope of line which cross the pMiddle.
     * @return point f [ ]
     */
    public static Point[] getIntersectionPoints(Point pMiddle, float radius, Float lineK) {
        Point[] points = new Point[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new Point((int) (pMiddle.x + xOffset), (int) (pMiddle.y - yOffset));
        points[1] = new Point((int) (pMiddle.x - xOffset), (int) (pMiddle.y + yOffset));

        return points;
    }

    /**
     * 字符串格式化为数字
     *
     * @param value the value
     * @return int
     */
    public static int string2Int(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取限定有最大高和最大宽的缩放比例
     *
     * @param maxWidth  the max width
     * @param maxHeight the max height
     * @param width     the width
     * @param height    the height
     * @return confine scaling
     */
    public static float getConfineScaling(float maxWidth, float maxHeight, float width, float height) {
        if (maxWidth == 0 || maxHeight == 0 || width == 0 || height == 0) {
            return 0;
        }
        float a = maxWidth / maxHeight;
        float b = width / height;
        if (a > b) {
            return maxHeight / height;
        } else {
            return maxWidth / width;
        }
    }

    /**
     * Gets confine scaling.
     * 获取限定有最大高和最大宽的缩放比例
     *
     * @param maxWidth  the max width
     * @param maxHeight the max height
     * @param width     the width
     * @param height    the height
     * @return the confine scaling
     */
    public static float getConfineScaling(int maxWidth, int maxHeight, int width, int height) {
        return getConfineScaling((float) maxWidth, (float) maxHeight, (float) width, (float) height);
    }

}
