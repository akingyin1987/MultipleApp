/*
 *
 *   Copyright (c) 2016 [akingyin@163.com]
 *
 *   Licensed under the Apache License, Version 2.0 (the "License”);
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.akingyin.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.akingyin.behaviour.animators.ChatAvatarsAnimator;
import com.akingyin.behaviour.animators.InSyncAnimator;
import com.akingyin.behaviour.animators.RocketAvatarsAnimator;
import com.akingyin.behaviour.animators.RocketFlightAwayAnimator;
import com.md.multipleapp.R;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ValueAnimator;
import com.redbooth.WelcomeCoordinatorLayout;

/**
 * @ Description:
 *
 * Company:重庆中陆承大科技有限公司
 * @ Author king
 * @ Date 2016/4/8 17:58
 * @ Version V1.0
 */
public class WelcomeActivity  extends AppCompatActivity {

  private boolean animationReady = false;
  private ValueAnimator backgroundAnimator;
  WelcomeCoordinatorLayout coordinatorLayout;
  private RocketAvatarsAnimator rocketAvatarsAnimator;
  private ChatAvatarsAnimator chatAvatarsAnimator;
  private RocketFlightAwayAnimator rocketFlightAwayAnimator;
  private InSyncAnimator inSyncAnimator;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);

    initializePages();
    initializeListeners();
    initializeBackgroundTransitions();
  }


  private void initializePages() {
    coordinatorLayout = (WelcomeCoordinatorLayout)findViewById(R.id.coordinator);
    if(null != coordinatorLayout){
      coordinatorLayout.addPage(R.layout.welcome_page_1,
          R.layout.welcome_page_2,
          R.layout.welcome_page_3,
          R.layout.welcome_page_4);
    }

  }

  private void initializeListeners() {
    coordinatorLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
      @Override
      public void onScrollPage(View v, float progress, float maximum) {
        if (!animationReady) {
          animationReady = true;
          backgroundAnimator.setDuration((long) maximum);
        }
        backgroundAnimator.setCurrentPlayTime((long) progress);
      }

      @Override
      public void onPageSelected(View v, int pageSelected) {
        switch (pageSelected) {
          case 0:
            if (rocketAvatarsAnimator == null) {
              rocketAvatarsAnimator = new RocketAvatarsAnimator(coordinatorLayout);
              rocketAvatarsAnimator.play();
            }
            break;
          case 1:
            if (chatAvatarsAnimator == null) {
              chatAvatarsAnimator = new ChatAvatarsAnimator(coordinatorLayout);
              chatAvatarsAnimator.play();
            }
            break;
          case 2:
            if (inSyncAnimator == null) {
              inSyncAnimator = new InSyncAnimator(coordinatorLayout);
              inSyncAnimator.play();
            }
            break;
          case 3:
            if (rocketFlightAwayAnimator == null) {
              rocketFlightAwayAnimator = new RocketFlightAwayAnimator(coordinatorLayout);
              rocketFlightAwayAnimator.play();
            }
            break;
        }
      }
    });
  }

  private void initializeBackgroundTransitions() {
    final Resources resources = getResources();
    final int colorPage1 = ResourcesCompat.getColor(resources, R.color.page1, getTheme());
    final int colorPage2 = ResourcesCompat.getColor(resources, R.color.page2, getTheme());
    final int colorPage3 = ResourcesCompat.getColor(resources, R.color.page3, getTheme());
    final int colorPage4 = ResourcesCompat.getColor(resources, R.color.page4, getTheme());
    backgroundAnimator = ValueAnimator
        .ofObject(new ArgbEvaluator(), colorPage1, colorPage2, colorPage3, colorPage4);
    backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        coordinatorLayout.setBackgroundColor((int) animation.getAnimatedValue());
      }
    });
  }
}
