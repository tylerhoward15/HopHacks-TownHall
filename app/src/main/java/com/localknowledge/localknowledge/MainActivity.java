package com.localknowledge.localknowledge;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

public class MainActivity extends AppCompatActivity {


    private int userZipCode;
    private int bttnCount = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView listView = (ListView) findViewById(R.id.listView);
        final ArrayList <String> lawCategory = new ArrayList<String>(asList("Economic", "Civil", "Criminal"));

        final ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lawCategory);
        listView.setAdapter(arrayAdapter);

        listView.setVisibility(View.INVISIBLE);
        listView.setY(listView.getY() + 2000f);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//              Toast.makeText(getApplicationContext(), lawCategory.get(i).toString(), Toast.LENGTH_LONG).show();
                if(lawCategory.get(i).equals("Economic")) {
                    startActivity(new Intent(MainActivity.this, EconomicLaws.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                }
                else if (lawCategory.get(i).equals("Civil")) {
                    startActivity(new Intent(MainActivity.this, CivilLaws.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                }
                else if (lawCategory.get(i).equals("Criminal")) {
                    startActivity(new Intent(MainActivity.this, CriminalLaws.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                }

            }
        });



        TextView textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setY(textView2.getY() + 2000f);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("New Laws in Your Area!")
                .setContentText("Click to view")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void checkZip(View v) {


        Button checkZIP = (Button) findViewById(R.id.checkZIP);
        EditText userZIP = (EditText) findViewById(R.id.userZIP);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        if(userZIP.getText().toString().length() != 0)
            userZipCode = Integer.parseInt(userZIP.getText().toString());

        ImageView image = (ImageView) findViewById(R.id.imageView2);



        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList <String> lawCategory = new ArrayList<String>();
        lawCategory.add("Economic");
        lawCategory.add("Civil");
        lawCategory.add("Criminal");

        ArrayAdapter <String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lawCategory);
        listView.setAdapter(arrayAdapter);





        if(zipCodeChecker(userZipCode)){

            InputMethodManager inputManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

//            textView2.setY(textView2.getY() + 2000f);
//            textView2.setVisibility(View.VISIBLE);
            checkZIP.animate().translationYBy(-2000f).setDuration(1000);
            userZIP.animate().translationYBy(-2000f).setDuration(1000);
            textView2.animate().scaleX(1.35f).scaleY(1.35f).setDuration(1000);
            textView2.animate().translationYBy(-2500f).setDuration(1000);
            listView.setVisibility(View.VISIBLE);
            listView.animate().translationYBy(-2000f).setDuration(1000);
            image.animate().alpha(0.0f).setDuration(800);


        }

        else if(userZIP.getText().toString().length() != 5)
            Toast.makeText(this,"Invalid ZIP Code", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Data not yet available in your location", Toast.LENGTH_LONG).show();


    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    public boolean zipCodeChecker(int enteredZipCode){

        userZipCode = enteredZipCode;

        String zip = "20601 20602 20603 20604 20606 20607 20608 20609 20610 20611 20612 20613 20615 20616 20617 20618 20619 20620 20621 20622 20623 20624 20625 20626 20627 20628 20629 20630 20632 20634 20635 20636 20637 20639 20640 20643 20645 20646 20650 20653 20656 20657 20658 20659 20660 20661 20662 20664 20667 20670 20674 20675 20676 20677 20678 20680 20682 20684 20685 20686 20687 20688 20689 20690 20692 20693 20695 20697 20701 20703 20704 20705 20706 20707 20708 20709 20710 20711 20712 20714 20715 20716 20717 20718 20719 20720 20721 20722 20723 20724 20725 20726 20731 20732 20733 20735 20736 20737 20738 20740 20741 20742 20743 20744 20745 20746 20747 20748 20749 20750 20751 20752 20753 20754 20755 20757 20758 20759 20762 20763 20764 20765 20768 20769 20770 20771 20772 20773 20774 20775 20776 20777 20778 20779 20781 20782 20783 20784 20785 20787 20788 20790 20791 20792 20794 20797 20799 20810 20811 20812 20813 20814 20815 20816 20817 20818 20824 20825 20827 20830 20832 20833 20837 20838 20839 20841 20842 20847 20848 20849 20850 20851 20852 20853 20854 20855 20857 20859 20860 20861 20862 20866 20868 20871 20872 20874 20875 20876 20877 20878 20879 20880 20882 20883 20884 20885 20886 20889 20891 20892 20894 20895 20896 20897 20898 20899 20901 20902 20903 20904 20905 20906 20907 20908 20910 20911 20912 20913 20914 20915 20916 20918 20993 20997 21001 21005 21009 21010 21012 21013 21014 21015 21017 21018 21020 21022 21023 21027 21028 21029 21030 21031 21032 21034 21035 21036 21037 21040 21041 21042 21043 21044 21045 21046 21047 21048 21050 21051 21052 21053 21054 21056 21057 21060 21061 21062 21065 21071 21074 21075 21076 21077 21078 21082 21084 21085 21087 21088 21090 21092 21093 21094 21098 21102 21104 21105 21106 21108 21111 21113 21114 21117 21120 21122 21123 21128 21130 21131 21132 21133 21136 21139 21140 21144 21146 21150 21152 21153 21154 21155 21156 21157 21158 21160 21161 21162 21163 21201 21202 21203 21204 21205 21206 21207 21208 21209 21210 21211 21212 21213 21214 21215 21216 21217 21218 21219 21220 21221 21222 21223 21224 21225 21226 21227 21228 21229 21230 21231 21233 21234 21235 21236 21237 21239 21240 21241 21244 21250 21251 21252 21263 21264 21265 21268 21270 21273 21274 21275 21278 21279 21280 21281 21282 21283 21284 21285 21286 21287 21288 21289 21290 21297 21298 21401 21402 21403 21404 21405 21409 21411 21412 21501 21502 21503 21504 21505 21520 21521 21522 21523 21524 21528 21529 21530 21531 21532 21536 21538 21539 21540 21541 21542 21543 21545 21550 21555 21556 21557 21560 21561 21562 21601 21606 21607 21609 21610 21612 21613 21617 21619 21620 21622 21623 21624 21625 21626 21627 21628 21629 21631 21632 21634 21635 21636 21638 21639 21640 21641 21643 21644 21645 21647 21648 21649 21650 21651 21652 21653 21654 21655 21656 21657 21658 21659 21660 21661 21662 21663 21664 21665 21666 21667 21668 21669 21670 21671 21672 21673 21675 21676 21677 21678 21679 21681 21682 21683 21684 21685 21686 21687 21688 21690 21701 21702 21703 21704 21705 21709 21710 21711 21713 21714 21715 21716 21717 21718 21719 21720 21721 21722 21723 21727 21733 21734 21737 21738 21740 21741 21742 21746 21747 21748 21749 21750 21754 21755 21756 21757 21758 21759 21762 21765 21766 21767 21769 21770 21771 21773 21774 21775 21776 21777 21778 21779 21780 21781 21782 21783 21784 21787 21788 21790 21791 21792 21793 21794 21795 21797 21798 21801 21802 21803 21804 21810 21811 21813 21814 21817 21821 21822 21824 21826 21829 21830 21835 21836 21837 21838 21840 21841 21842 21843 21849 21850 21851 21852 21853 21856 21857 21861 21862 21863 21864 21865 21866 21867 21869 21871 21872 21874 21875 21890 21901 21902 21903 21904 21911 21912 21913 21914 21915 21916 21917 21918 21919 21920 21921 21922 21930";

        List<String> zipStrings = new ArrayList<>(Arrays.asList(zip.split(" ")));

        if(zipStrings.contains(Integer.toString(userZipCode)))
            return true;
        else return false;


    }
}