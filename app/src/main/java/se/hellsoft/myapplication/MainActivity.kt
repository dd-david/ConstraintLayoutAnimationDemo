package se.hellsoft.myapplication

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.OvershootInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  /*
  * Todo: Kotlin 에서 타입 뒤에 따라붙은 '?'은 어떤 의미인지 확인해보자
  *       Optional(Nullable)
  *       자료형으로 null 참조를 할 수 있는것과/없는것으로 구분됩니다
  *       타입을 검사하고 > null 여부를 확인하고 > 그 시점에 해당 자료형으로 형변환 합니다
  *       일반적으로 Optional 이라고 불리고, Kotlin 에서는 Nullable 이라고 합니다
  *
  * Todo: 코드에서 변수이름 아래에 언더라인이 붙는 이유는 무엇인지 확인해보자
  *
  * */
  private var selectedView: View? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    /*
    * 화면의 타이틀을 없애고, 풀스크린으로 맞추도록 설정합니다
    *
    * */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                         WindowManager.LayoutParams.FLAG_FULLSCREEN);

    /* 기본적으로 activity_main 레이아웃을 참조해서 그리게 합니다 */
    setContentView(R.layout.activity_main)


    selectedView = null

    /*
    * 왼쪽버튼을 눌렀을때,
    * 이미 왼쪽 아이콘이 눌린게 아니라면, android_main_left 레이아웃으로 변경합니다
    * 이미 왼쪽 아이콘이 눌린 상태였다면, 다시 원복합니다
    *
    * */
    left.setOnClickListener {
      if (selectedView != left) {
        updateConstraints(R.layout.activity_main_left)
        selectedView = left
      } else {
        toDefault()
      }
    }

    /*
    * 중간버튼을 눌렀을때,
    * 이미 중간 아이콘이 눌린게 아니라면, android_main_middle 레이아웃으로 변경합니다
    * 이미 중간 아이콘이 눌린 상태였다면, 다시 원복합니다
    *
    * */
    middle.setOnClickListener {
      if (selectedView != middle) {
        updateConstraints(R.layout.activity_main_middle)
        selectedView = middle
      } else {
        toDefault()
      }
    }

    /*
    * 중간버튼을 눌렀을때,
    * 이미 중간 아이콘이 눌린게 아니라면, android_main_middle 레이아웃으로 변경합니다
    * 이미 중간 아이콘이 눌린 상태였다면, 다시 원복합니다
    *
    * */
    right.setOnClickListener {
      if (selectedView != right) {
        updateConstraints(R.layout.activity_main_right)
        selectedView = right
      } else {
        toDefault()
      }
    }

    root.setOnClickListener {
      toDefault()
    }
  }

  private fun toDefault() {
    if (selectedView != null) {
      updateConstraints(R.layout.activity_main)
      selectedView = null
    }
  }

  /*
  * @param id layoutId(Res)
  * 화면을 전달한 파라미터 레이아웃으로 전환합니다
  *
  * Todo: @LayoutRes 는 별도로 세팅해줄 필요가 있나?
  * */
  fun updateConstraints(@LayoutRes id: Int) {
    /*
    * android.support.constraint.ConstraintSet
    * ConstraintLayout 을 프로그래밍을 통해서 정의할 수 있는 방법을 제공합니다
    *
    * clone(Context, R.resId): 전달받은 resId 로 부터 ConstraintLayout 을 생성해냅니다
    * applyTo(ConstraintLayout): constraints 들을 파라미터로 전달받은 ConstraintLayout 에 적용합니다
    *   이 앱의 경우, ConstraintLayout 으로 구성되어 있어서, root 를 전달합니다
    * */
    val newConstraintSet = ConstraintSet()
    newConstraintSet.clone(this, id)
    newConstraintSet.applyTo(root)

    /*
    * android.transition.ChangeBounds
    * 레이아웃을 변경할때, 적용할 수 있는 에니메이션 효과를 세팅합니다
    *
    * interpolator: 전환시킬때 사용되는 효과
    * duration: 전환에 사용되는 시간
    * startDelay: 전환 시작까지 주는 딜레이
    *
    * */
    val transition = ChangeBounds()
    transition.interpolator = OvershootInterpolator()
    TransitionManager.beginDelayedTransition(root, transition)
  }
}
