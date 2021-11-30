package com.gracefulwind.learnarms.newwrite.widget;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.gracefulwind.learnarms.newwrite.widget.SmartHandNote.MODE_ERASER;
import static com.gracefulwind.learnarms.newwrite.widget.SmartHandNote.MODE_TEXT_BOX;
import static com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView.MODE_DOODLE;
import static com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView.MODE_TEXT;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @ClassName: ViewMode
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/30
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/30
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@Retention(SOURCE)
@IntDef(value = {MODE_TEXT, MODE_DOODLE, MODE_ERASER, MODE_TEXT_BOX})
public @interface ViewMode {
}
