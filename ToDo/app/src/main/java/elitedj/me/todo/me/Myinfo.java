package elitedj.me.todo.me;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elitedj.me.todo.R;

public class Myinfo extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lv1;

    private int[] imagesId={R.drawable.name,R.drawable.sex,R.drawable.birthday,R.drawable.email,R.drawable.tel,R.drawable.school,R.drawable.exit};
    private	String[] names={"蔡徐坤","男","1999-07-30","842184122@qq.com","13268453217","SCNU","退出登录"};
    private String[] contents={"更改昵称","更改性别","更改生日","更改邮箱","更改电话","更改学校","退出该账号"};
    private	String[] listdb={"name","sex","birth","email","tel","school","out"};
    private TextView usename;
    private MyBaseAdapter mAdapter;
    private PersonDB DB;
    private SettingDB SDB;
    private SQLiteDatabase dbread,dread2;
    private SQLiteDatabase dbw;
    private ImageView touxiang,touxiang2;
    private Setting set;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SDB = new SettingDB(this);
        dread2 = SDB.getReadableDatabase();
        set = new Setting();
        inittheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        checkPermission();
        lv1 =  findViewById(R.id.listView2);
        mAdapter = new MyBaseAdapter();
        lv1.setAdapter(mAdapter);
        Log.d("my","asd");
        lv1.setOnItemClickListener(Myinfo.this);
        usename = findViewById(R.id.username1);
        //创建新的列时要删除一下数据库
        //this.deleteDatabase("Person");
        DB = new PersonDB(this);
        dbread = DB.getReadableDatabase();
        dbw   = DB.getWritableDatabase();
        touxiang = (ImageView) findViewById(R.id.home_listview_logo);
        //用LayoutInflater加载布局

        LayoutInflater factory = LayoutInflater.from(Myinfo.this);

//获取dialog布局文件获取View
        final View MeView = factory.inflate(R.layout.activity_me, null);
// 通过textEntryView来获取控件
        touxiang2  = (ImageView) MeView.findViewById(R.id.touxiang);

        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            touxiang.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }

//        ContentValues values = new ContentValues();
//        values.put("_id",1);
//        values.put("name","皇子吧");
//        values.put("sex","男");
//        values.put("birth","1999-07-30");
//        values.put("email","812121151@qq.com");
//        values.put("tel","13254662148");
//        values.put("school","SCNU");
//        values.put("out","退出登录");
//        dbw.insert("Person",null,values);
        //dbw.close();

        // 清空数据库表中内容
        //dbread.execSQL("delete from note");

        getData();
        //RefreshNotesList();
    }
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        touxiang.setImageBitmap(head);// 用ImageButton显示出来
                        touxiang2.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void getData() {
        Cursor cursor = dbread.query("Person", null, null, null, null,
                null, null);
        int count = cursor.getCount();
        String str =String.valueOf(count);
        if(count==0)
        {
            Toast.makeText(this,"meiyou",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
            cursor.moveToNext();
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String birth = cursor.getString(cursor.getColumnIndex("birth"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            String school = cursor.getString(cursor.getColumnIndex("school"));
            //Map<String, Object> map = new HashMap<String, Object>();

            names[0] = name;
            usename.setText(name);
            names[1] = sex;
            names[2] = birth;
            names[3] = email;
            names[4] = tel;
            names[5] = school;
            names[6] = "退出登录";
        }

        cursor.close();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(contents[position]);

        Log.d("my","qwe");
        final EditText input = new EditText(this);
        input.setTextColor(R.attr.colorPrimary);
        builder.setView(input);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String col = listdb[position];
                String updatesql = "update Person set "+col+" = '"+ input.getText().toString()+"'";
                dbread.execSQL(updatesql);
                getData();
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();


//        switch (position)
//        {
//
//        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent =this.getIntent();
            Bundle bundle = intent.getExtras();
            bundle.putString("result", "aaaaaaaaaaa");
            intent.putExtras(bundle);
            /*
             * 调用setResult方法将Intent对象返回给第一个Activity，第一个Activity就可以在onActivityResult方法中得到Intent对象，
             */
            setResult(1, intent);
            finish();
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //android6.0之后要动态获取权限
    private void checkPermission() {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(Myinfo.this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(Myinfo.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    class MyBaseAdapter extends BaseAdapter {
        @Override
        public long getItemId(int position) {
            // TODO 自动生成的方法存根
            return position;
        }

        @Override
        public Object getItem(int position) {
            // TODO 自动生成的方法存根
            return names[position];
        }

        @Override
        public int getCount() {
            // TODO 自动生成的方法存根
            return names.length;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO 自动生成的方法存根
            View layout=View.inflate(Myinfo.this, R.layout.listview, null);
            ImageView face = (ImageView)layout.findViewById(R.id.face);
            TextView name =(TextView)layout.findViewById(R.id.name);
            TextView mark = (TextView)layout.findViewById(R.id.mark);

            face.setImageResource(imagesId[position]);
            name.setText(names[position]);
            mark.setText(contents[position]);

            return layout;
        }

    }

    public void inittheme() {
        Cursor cursor = dread2.query("Setting", null, null, null, null,null, null);

        cursor.moveToNext();
        set.setTheme(cursor.getInt(cursor.getColumnIndex("theme")));
        switch (set.getTheme())
        {
            case 0:
                setTheme(R.style.GreenAppTheme);
                break;
            case 1:
                setTheme(R.style.BlueAppTheme);
                break;
            case 2:
                setTheme(R.style.PurpleAppTheme);
                break;
            case 3:
                setTheme(R.style.BronzeAppTheme);
                break;
            case 4:
                setTheme(R.style.PinkAppTheme);
                break;
            case 5:
                setTheme(R.style.OrAppTheme);
                break;

            default:
                break;
        }
        cursor.close();
    }
}
