package com.zry.base.common.view.group;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;


/**
 * 设置背景
 *
 * @author : ZhaoRuYang
 * @date : 2018/7/17
 */
public class GroupDecoration extends RecyclerView.ItemDecoration {

    private Paint fillPaint;
    private Path cornerShadowPath;
    private float cornerRadius;
    private float shadowSize = 10;
    private Paint cornerShadowPaint;

    private int shadowStartColor = Color.parseColor("#0D000000");
    private int shadowEndColor = Color.parseColor("#00000000");
    private Paint edgeShadowPaint;

    private SparseArray<GroupInfo> groupInfos;


    public GroupDecoration() {

        cornerShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        cornerShadowPaint.setStyle(Paint.Style.FILL);
        cornerRadius = 10;
        edgeShadowPaint = new Paint(cornerShadowPaint);
        edgeShadowPaint.setAntiAlias(false);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        buildShadowCorners();
    }


    public void setGroupInfos(SparseArray<GroupInfo> infos) {
        groupInfos = infos;
    }


    public void setGroupInfo(SparseArray<GroupInfo> groupInfo,int start, int end, String backGroundColor) {

        if (start == end) {
            return;
        }

        if (end - start == 1) {
            GroupInfo info = new GroupInfo(GroupInfo.Type.all, backGroundColor);
            groupInfo.put(start, info);
            return;
        }

        for (int i = start; i < end; i++) {
            int type;
            if (i == start) {
                type = GroupInfo.Type.top;
            } else if (i == end - 1) {
                type = GroupInfo.Type.bottom;
            } else {
                type = GroupInfo.Type.mid;
            }
            GroupInfo info = new GroupInfo(type, backGroundColor);
            groupInfo.put(i, info);
        }


    }


    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            int right = childView.getRight();
            int top = childView.getTop();
            int bottom = childView.getBottom();
            int left = childView.getLeft();
            RectF bounds = new RectF(left, top, right, bottom);
            int childAdapterPosition = parent.getChildAdapterPosition(childView);
            updateBackground(childAdapterPosition, canvas, bounds);
        }
    }


    private void updateBackground(int key, Canvas canvas, RectF bounds) {

        if (groupInfos == null) {
            return;
        }

        GroupInfo groupInfo = groupInfos.get(key);
        if (groupInfo == null) {
            return;
        }

        if (bounds == null) {
            return;
        }

        int colorInt = Color.WHITE;
        try {
            String color = groupInfo.getColor();
            colorInt = Color.parseColor(color);
            //colorInt = Color.RED;
        } catch (Exception e) {
        }

        fillPaint.setColor(colorInt);

        if (groupInfo.getType() == GroupInfo.Type.all) {
            drawAll(canvas, bounds);
        } else if (groupInfo.getType() == GroupInfo.Type.top) {
            drawHalf(canvas, bounds, true);
        } else if (groupInfo.getType() == GroupInfo.Type.mid) {
            drawMid(canvas, bounds);
        } else if (groupInfo.getType() == GroupInfo.Type.bottom) {
            drawHalf(canvas, bounds, false);
        }

    }



    private void drawAll(Canvas canvas, RectF bounds) {
        RectF newR = new RectF(bounds);
        newR.inset(-shadowSize, -shadowSize);
        drawRoundRectShadow(canvas, newR);
        drawRoundRect(canvas, bounds);
    }

    private void drawMid(Canvas canvas, RectF bounds) {
        RectF newR = new RectF(bounds);
        newR.left -= shadowSize;
        newR.right += shadowSize;
        drawRectangleShadow(canvas, newR);
        drawRectangle(canvas, bounds);
    }


    private void drawRectangle(Canvas canvas, RectF bounds) {
        canvas.drawRect(bounds, fillPaint);
    }


    private void drawRectangleShadow(Canvas canvas, RectF mCardBounds) {
        final float edgeShadowTop = -cornerRadius - shadowSize;
        final float inset = cornerRadius + shadowSize;
        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.bottom - inset);
        canvas.rotate(270f);
        if (drawVerticalEdges) {
            canvas.drawRect(-inset, edgeShadowTop,
                    mCardBounds.height() - inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RT
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.top + inset);
        canvas.rotate(90f);
        if (drawVerticalEdges) {
            canvas.drawRect(-inset, edgeShadowTop,
                    mCardBounds.height() - inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
    }


    private void drawHalf(Canvas canvas, RectF bounds, boolean top) {

        int allSave = canvas.save();

        if (!top) {
            canvas.rotate(180, bounds.centerX(), bounds.centerY());
        }

        RectF newR = new RectF(bounds);
        newR.top -= shadowSize;
        newR.left -= shadowSize;
        newR.right += shadowSize;
        drawHalfTopShadow(canvas, newR);
        drawHalfTop(canvas, bounds);


        canvas.restoreToCount(allSave);

    }

    private void drawHalfTopShadow(Canvas canvas, RectF mCardBounds) {

        final float edgeShadowTop = -cornerRadius - shadowSize;
        final float inset = cornerRadius + shadowSize;
        final boolean drawHorizontalEdges = mCardBounds.width() - 2 * inset > 0;
        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.top + inset);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset,
                    -cornerRadius,
                    edgeShadowPaint);
        }
        canvas.restoreToCount(saved);

        // LB
        saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.bottom - inset);
        canvas.rotate(270f);
        //canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(-inset, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RT
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.top + inset);
        canvas.rotate(90f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.height() - inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);

    }

    private void drawHalfTop(Canvas canvas, RectF bounds) {
        final float twoRadius = cornerRadius * 2;
        final float innerWidth = bounds.width() - twoRadius;
        // center
        canvas.drawRect(bounds.left, bounds.top + cornerRadius,
                bounds.right, bounds.bottom, fillPaint);

        if (cornerRadius >= 1f) {

            float roundedCornerRadius = cornerRadius;
            canvas.drawRect(bounds.left + roundedCornerRadius, bounds.top,
                    bounds.right - roundedCornerRadius,
                    bounds.top + roundedCornerRadius, fillPaint);

            RectF mCornerRect = new RectF();
            mCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius,
                    roundedCornerRadius);
            int saved = canvas.save();
            canvas.translate(bounds.left + roundedCornerRadius,
                    bounds.top + roundedCornerRadius);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);
            canvas.translate(innerWidth, 0);
            canvas.rotate(90);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);

            canvas.restoreToCount(saved);

        }
    }


    private void drawRoundRect(Canvas canvas, RectF bounds) {
        final float twoRadius = cornerRadius * 2;
        final float innerWidth = bounds.width() - twoRadius;
        final float innerHeight = bounds.height() - twoRadius;
        if (cornerRadius >= 1f) {
            float roundedCornerRadius = cornerRadius;
            RectF mCornerRect = new RectF();
            mCornerRect.set(-roundedCornerRadius, -roundedCornerRadius, roundedCornerRadius,
                    roundedCornerRadius);
            int saved = canvas.save();
            canvas.translate(bounds.left + roundedCornerRadius,
                    bounds.top + roundedCornerRadius);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);
            canvas.translate(innerWidth, 0);
            canvas.rotate(90);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);
            canvas.translate(innerHeight, 0);
            canvas.rotate(90);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);
            canvas.translate(innerWidth, 0);
            canvas.rotate(90);
            canvas.drawArc(mCornerRect, 180, 90, true, fillPaint);
            canvas.restoreToCount(saved);
            canvas.drawRect(bounds.left + roundedCornerRadius, bounds.top,
                    bounds.right - roundedCornerRadius,
                    bounds.top + roundedCornerRadius, fillPaint);

            canvas.drawRect(bounds.left + roundedCornerRadius,
                    bounds.bottom - roundedCornerRadius,
                    bounds.right - roundedCornerRadius, bounds.bottom, fillPaint);
        }
        // center
        canvas.drawRect(bounds.left, bounds.top + cornerRadius,
                bounds.right, bounds.bottom - cornerRadius, fillPaint);
    }

    private void drawRoundRectShadow(Canvas canvas, RectF mCardBounds) {
        final float edgeShadowTop = -cornerRadius - shadowSize;
        final float inset = cornerRadius + shadowSize;
        final boolean drawHorizontalEdges = mCardBounds.width() - 2 * inset > 0;
        final boolean drawVerticalEdges = mCardBounds.height() - 2 * inset > 0;
        // LT
        int saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.top + inset);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.width() - 2 * inset,
                    -cornerRadius,
                    edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RB
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.bottom - inset);
        canvas.rotate(180f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawHorizontalEdges) {
            canvas.drawRect(0,
                    edgeShadowTop,
                    mCardBounds.width() - 2 * inset,
                    -cornerRadius,
                    edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // LB
        saved = canvas.save();
        canvas.translate(mCardBounds.left + inset, mCardBounds.bottom - inset);
        canvas.rotate(270f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
        // RT
        saved = canvas.save();
        canvas.translate(mCardBounds.right - inset, mCardBounds.top + inset);
        canvas.rotate(90f);
        canvas.drawPath(cornerShadowPath, cornerShadowPaint);
        if (drawVerticalEdges) {
            canvas.drawRect(0, edgeShadowTop,
                    mCardBounds.height() - 2 * inset, -cornerRadius, edgeShadowPaint);
        }
        canvas.restoreToCount(saved);
    }


    private void buildShadowCorners() {
        RectF innerBounds = new RectF(-cornerRadius, -cornerRadius, cornerRadius, cornerRadius);
        RectF outerBounds = new RectF(innerBounds);
        outerBounds.inset(-shadowSize, -shadowSize);

        if (cornerShadowPath == null) {
            cornerShadowPath = new Path();
        } else {
            cornerShadowPath.reset();
        }
        cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        cornerShadowPath.moveTo(-cornerRadius, 0);
        cornerShadowPath.rLineTo(-shadowSize, 0);
        // outer arc
        cornerShadowPath.arcTo(outerBounds, 180f, 90f, false);
        // inner arc
        cornerShadowPath.arcTo(innerBounds, 270f, -90f, false);
        cornerShadowPath.close();
        float startRatio = cornerRadius / (cornerRadius + shadowSize);
        cornerShadowPaint.setShader(new RadialGradient(0, 0, cornerRadius + shadowSize,
                new int[]{shadowStartColor, shadowStartColor, shadowEndColor},
                new float[]{0f, startRatio, 1f},
                Shader.TileMode.CLAMP));

        edgeShadowPaint.setShader(new LinearGradient(0, -cornerRadius + shadowSize, 0,
                -cornerRadius - shadowSize,
                new int[]{shadowStartColor, shadowStartColor, shadowEndColor},
                new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP));
        edgeShadowPaint.setAntiAlias(false);
    }

}
