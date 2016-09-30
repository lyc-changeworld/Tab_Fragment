package com.example.achuan.tab_fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.achuan.tab_fragment.App.Constants;
import com.example.achuan.tab_fragment.fragment.ContactsFragment;
import com.example.achuan.tab_fragment.fragment.MessageFragment;
import com.example.achuan.tab_fragment.fragment.NewsFragment;
import com.example.achuan.tab_fragment.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by achuan 2016/9/29
 * 功能：项目的主Activity,所有的Fragment都嵌入在这,动态的添加碎片
 **/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.message_image)
    ImageView mMessageImage;
    @BindView(R.id.message_text)
    TextView mMessageText;
    @BindView(R.id.message_layout)
    RelativeLayout mMessageLayout;
    @BindView(R.id.contacts_image)
    ImageView mContactsImage;
    @BindView(R.id.contacts_text)
    TextView mContactsText;
    @BindView(R.id.contacts_layout)
    RelativeLayout mContactsLayout;
    @BindView(R.id.news_image)
    ImageView mNewsImage;
    @BindView(R.id.news_text)
    TextView mNewsText;
    @BindView(R.id.news_layout)
    RelativeLayout mNewsLayout;
    @BindView(R.id.setting_image)
    ImageView mSettingImage;
    @BindView(R.id.setting_text)
    TextView mSettingText;
    @BindView(R.id.setting_layout)
    RelativeLayout mSettingLayout;

    private MessageFragment messageFragment;//消息
    private ContactsFragment contactsFragment;//联系人
    private NewsFragment newsFragment;//动态
    private SettingFragment settingFragment;//设置
    /***
     * 用于对Fragment进行管理
     ***/
    private FragmentManager fragmentManager;
    //初始化隐藏和显示的碎片的类型
    private int hideFragment = Constants.TYPE_MESSAGE;//记录上一次显示的碎片
    private int showFragment = Constants.TYPE_MESSAGE;//记录这一次将显示的碎片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化布局
        ButterKnife.bind(this);
        //第一次创建activity时才进行实例化,防止重复创建
        if(savedInstanceState==null)
        {
            fragmentManager = getFragmentManager();//获取到碎片管理者的实例
            // 第一次启动时选中默认的fragment
            setTabSelection(showFragment);
        }
    }
    /**
    * Tab的点击监听事件
    **/
    @OnClick({R.id.message_layout, R.id.contacts_layout, R.id.news_layout, R.id.setting_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.message_layout:
                showFragment = Constants.TYPE_MESSAGE;
                break;
            case R.id.contacts_layout:
                showFragment = Constants.TYPE_CONTACTS;
                break;
            case R.id.news_layout:
                showFragment = Constants.TYPE_NEWS;
                break;
            case R.id.setting_layout:
                showFragment = Constants.TYPE_SETTING;
                break;
        }
        //两次点击是不同的tab时才进行切换
        if (showFragment != hideFragment) {
            setTabSelection(showFragment);//显示当前的fragment
        }
        hideFragment = showFragment;//当前点击的tab又变成了过去式了
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int showFragment) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //重置UI效果和隐藏历史的fragment
        clearWITHhide(hideFragment, transaction);
        switch (showFragment) {
            case Constants.TYPE_MESSAGE:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                mMessageImage.setImageResource(R.drawable.message_selected);
                mMessageText.setTextColor(Color.WHITE);
                if (messageFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    messageFragment = new MessageFragment();
                    //通过事务往容器中添加碎片实例（第一个参数为容器,第二个为待添加的实例）
                    transaction.add(R.id.content, messageFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(messageFragment);
                }
                break;
            case Constants.TYPE_CONTACTS:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                mContactsImage.setImageResource(R.drawable.contacts_selected);
                mContactsText.setTextColor(Color.WHITE);
                if (contactsFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    contactsFragment = new ContactsFragment();
                    transaction.add(R.id.content, contactsFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(contactsFragment);
                }
                break;
            case Constants.TYPE_NEWS:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                mNewsImage.setImageResource(R.drawable.news_selected);
                mNewsText.setTextColor(Color.WHITE);
                if (newsFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    newsFragment = new NewsFragment();
                    transaction.add(R.id.content, newsFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(newsFragment);
                }
                break;
            case Constants.TYPE_SETTING:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                mSettingImage.setImageResource(R.drawable.setting_selected);
                mSettingText.setTextColor(Color.WHITE);
                if (settingFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(settingFragment);
                }
                break;
            default:
                break;
        }
        //提交事务,调用commit()方法来完成
        transaction.commit();
    }
    /**
     * 功能：刷新历史的ui显示效果和隐藏历史显示的fragment
     */
    private void clearWITHhide(int hideFragment, FragmentTransaction transaction) {
        switch (hideFragment) {
            case Constants.TYPE_MESSAGE:
                mMessageImage.setImageResource(R.drawable.message_unselected);
                mMessageText.setTextColor(Color.parseColor("#82858b"));
                if (messageFragment != null) {
                    transaction.hide(messageFragment);//隐藏fragment
                }
                break;
            case Constants.TYPE_CONTACTS:
                mContactsImage.setImageResource(R.drawable.contacts_unselected);
                mContactsText.setTextColor(Color.parseColor("#82858b"));
                if (contactsFragment != null) {
                    transaction.hide(contactsFragment);
                }
                break;
            case Constants.TYPE_NEWS:
                mNewsImage.setImageResource(R.drawable.news_unselected);
                mNewsText.setTextColor(Color.parseColor("#82858b"));
                if (newsFragment != null) {
                    transaction.hide(newsFragment);
                }
                break;
            case Constants.TYPE_SETTING:
                mSettingImage.setImageResource(R.drawable.setting_unselected);
                mSettingText.setTextColor(Color.parseColor("#82858b"));
                if (settingFragment != null) {
                    transaction.hide(settingFragment);
                }
                break;
            default:
                break;
        }
    }


}
