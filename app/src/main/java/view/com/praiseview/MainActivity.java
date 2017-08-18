package view.com.praiseview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private ImageView faceImages;

    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceImages= (ImageView) findViewById(R.id.faceImages);

        seekBar= (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) faceImages.getLayoutParams();

                lp.bottomMargin=progress*3;

                faceImages.setLayoutParams(lp);

            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    /**
     * 内存泄漏，过多引发内存溢出
     *
     * 单列模式。使用applicationContext
     * 非静态内部类默认持有外部类的对象，使用静态内部类，
     *
     * 线程，使用静态内部类线程，activity销毁时，结束线程。
     *
     * 一些资源入Bitmap，service，broadcast在activity销毁时应该及时回收，NULL值
     */
}
