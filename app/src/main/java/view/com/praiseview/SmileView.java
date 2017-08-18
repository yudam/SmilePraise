package view.com.praiseview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/8/9.
 */

public class SmileView extends LinearLayout implements Animator.AnimatorListener{

    private int type;

    private boolean icClose;

    private int like=60;

    private int disLike=20;

    private String defaluteShadow = "#7F484848";
    private int defatColor=Color.WHITE;
    private String defatLike="喜欢";
    private String defatdisLike="不喜欢";

    private int defatMargin=20;
    private int defatBottom=60;

    private int defautSize=dip2px(getContext(),30);

    private LinearLayout likeBack;
    private LinearLayout disLikeBack;

    private LinearLayout likeLinear;
    private LinearLayout disLikeLinear;

    private ImageView likeImage;
    private TextView likeNum;
    private TextView likeText;

    private ImageView disLikeImage;
    private TextView disLikeNum;
    private TextView disLikeText;

    private AnimationDrawable likeAnimation;
    private AnimationDrawable disLikeAnimation;

    public SmileView(Context context) {
        super(context);
    }

    public SmileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();

        bindClick();
    }

    public SmileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();

        bindClick();
    }

    private void initView() {
        //布局参数
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
        setBackgroundColor(Color.TRANSPARENT);

        //初始化ImageView喜欢

        likeImage=new ImageView(getContext());

        likeImage.setBackgroundResource(R.drawable.animation_like);
        likeAnimation= (AnimationDrawable) likeImage.getBackground();
        likeNum=new TextView(getContext());
        likeNum.setText(like+"%");
        likeNum.setTextSize(20f);
        likeNum.setTextColor(defatColor);
        TextPaint mLikeTextPaint=likeNum.getPaint();
        mLikeTextPaint.setFakeBoldText(true);
        likeText=new TextView(getContext());
        likeText.setText(defatLike);
        likeText.setTextColor(defatColor);
        likeText.setTextSize(20f);

        //不喜欢
        disLikeImage=new ImageView(getContext());

        disLikeImage.setBackgroundResource(R.drawable.animation_dislike);
        disLikeAnimation= (AnimationDrawable) disLikeImage.getBackground();
        disLikeNum=new TextView(getContext());
        disLikeNum.setText(disLike+"%");
        disLikeNum.setTextSize(20f);
        disLikeNum.setTextColor(defatColor);
        TextPaint mdisLikeTextPaint=disLikeNum.getPaint();
        mdisLikeTextPaint.setFakeBoldText(true);
        disLikeText=new TextView(getContext());
        disLikeText.setText(defatdisLike);
        disLikeText.setTextColor(defatColor);
        disLikeText.setTextSize(20f);


        //初始化布局
        likeBack=new LinearLayout(getContext());
        disLikeBack=new LinearLayout(getContext());
        LinearLayout.LayoutParams lp0=new LayoutParams(defautSize,defautSize);
        likeBack.addView(likeImage,lp0);
        disLikeBack.addView(disLikeImage,lp0);
        likeBack.setBackgroundResource(R.drawable.linear_white);
        disLikeBack.setBackgroundResource(R.drawable.linear_white);

        //单列布局
        likeLinear=new LinearLayout(getContext());
        likeLinear.setOrientation(VERTICAL);
        likeLinear.setGravity(Gravity.RIGHT);
        likeLinear.setBackgroundColor(Color.TRANSPARENT);
        disLikeLinear=new LinearLayout(getContext());
        disLikeLinear.setOrientation(VERTICAL);
        disLikeLinear.setGravity(Gravity.CENTER_HORIZONTAL);
        disLikeLinear.setBackgroundColor(Color.TRANSPARENT);

        //添加文字图片
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp.setMargins(0,10,0,0);
        lp.gravity=Gravity.CENTER_HORIZONTAL;



        likeLinear.addView(likeNum,lp);
        likeLinear.addView(likeText,lp);
        likeLinear.addView(likeBack,lp);
        disLikeLinear.addView(disLikeNum,lp);
        disLikeLinear.addView(disLikeText,lp);
        disLikeLinear.addView(disLikeBack,lp);

        //分割线

        ImageView linesImage=new ImageView(getContext());
        linesImage.setBackground(new ColorDrawable(Color.GRAY));

        LinearLayout.LayoutParams lp2=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(defatMargin,10,defatMargin,defatBottom+20);
        lp2.gravity=Gravity.BOTTOM;

        LinearLayout.LayoutParams lp3=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp3.setMargins(30,10,30,defatBottom);
        lp3.gravity=Gravity.BOTTOM;

        //添加所有子布局
        addView(disLikeLinear,lp3);
        addView(linesImage,lp2);
        addView(likeLinear,lp3);

        //隐藏图片文字

        setVisible(View.INVISIBLE);

    }

    //隐藏文字和数字

    public void setVisible(int v){

        likeNum.setVisibility(v);

        likeText.setVisibility(v);

        disLikeNum.setVisibility(v);

        disLikeText.setVisibility(v);
    }

    private void bindClick() {
        likeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                type=1;
                animShow();
                setVisible(View.VISIBLE);

                setBackgroundColor(Color.parseColor(defaluteShadow));

                //设置被点击的背景颜色
                likeBack.setBackgroundResource(R.drawable.linear_bg);
                disLikeBack.setBackgroundResource(R.drawable.linear_white);

                //重置帧动画
                likeImage.setBackground(null);
                likeImage.setBackgroundResource(R.drawable.animation_like);
                likeAnimation= (AnimationDrawable) likeImage.getBackground();

            }
        });

        disLikeImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                type=2;
                animShow();
                setVisible(View.VISIBLE);

                setBackgroundColor(Color.parseColor(defaluteShadow));

                disLikeBack.setBackgroundResource(R.drawable.linear_bg);
                likeBack.setBackgroundResource(R.drawable.linear_white);

                disLikeImage.setBackground(null);
                disLikeImage.setBackgroundResource(R.drawable.animation_dislike);
                disLikeAnimation= (AnimationDrawable) disLikeImage.getBackground();
            }
        });

    }

//展开动画
    public void animShow(){

        int max=Math.max(like*4,disLike*4);

        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,max);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int magin= (int) animation.getAnimatedValue();

                LinearLayout.LayoutParams lp= (LayoutParams) likeImage.getLayoutParams();
                lp.bottomMargin=magin;

                if(magin<like*4){

                    likeImage.setLayoutParams(lp);
                }

                if(magin<disLike*4){

                    disLikeImage.setLayoutParams(lp);
                }

            }
        });
        icClose=false;
        valueAnimator.setDuration(500);
        valueAnimator.addListener(this);
        valueAnimator.start();
    }

//关闭动画
    public void setBackUp(){

        likeImage.setClickable(false);
        disLikeImage.setClickable(false);

        int max=Math.max(like*4,disLike*4);

        ValueAnimator valueAnimator=ValueAnimator.ofInt(max,0);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int magin= (int) animation.getAnimatedValue();

                LinearLayout.LayoutParams lp= (LayoutParams) likeImage.getLayoutParams();
                lp.bottomMargin=magin;

                if(magin<like*4){

                    likeImage.setLayoutParams(lp);
                }

                if(magin<disLike*4){

                    disLikeImage.setLayoutParams(lp);
                }

            }
        });

        valueAnimator.setDuration(500);
        valueAnimator.addListener(this);
        valueAnimator.start();
    }


    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

        //重置帧动画
        likeAnimation.stop();
        disLikeAnimation.stop();

        if(icClose){
            likeImage.setClickable(true);
            disLikeImage.setClickable(true);
            setVisible(View.INVISIBLE);
            setBackgroundColor(Color.TRANSPARENT);
            return;
        }
        icClose=true;

        if(type==1){

            likeAnimation.start();

            objectY(likeImage);

        }else{

            disLikeAnimation.start();
            objectX(disLikeImage);
        }

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    public void objectY(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        //animator.setRepeatCount(1);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }
        });
    }

    public void objectX(View view) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -10.0f, 0.0f, 10.0f, 0.0f, -10.0f, 0.0f, 10.0f, 0);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        // animator.setRepeatCount(1);
        animator.setDuration(1500);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setBackUp(); //执行回弹动画
            }
        });
    }


    //dp转px
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
