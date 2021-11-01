package com.gracefulwind.learnarms.write.widget.doodle;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.gracefulwind.learnarms.write.widget.doodle.OperationPresenter.MODE_DOODLE;
import static com.gracefulwind.learnarms.write.widget.doodle.OperationPresenter.MODE_ERASER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @ClassName: EditMode
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/10
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/10
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Retention(SOURCE)
@IntDef(value = {MODE_DOODLE, MODE_ERASER})
public @interface EditMode {

}
