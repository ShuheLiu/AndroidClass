package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myapplication.api.Client;
import com.example.myapplication.api.Service;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCourseFragment extends Fragment {
    private static final String ARG_USER = "user";
    private User user;
    private List<Course> CourseList2;
    private List<UserCourse> UserCourseList;
    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
    private ListView myCourselistview;
    private String userid;

    private OnFragmentInteractionListener mListener;

    public MyCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user user.
     * @return A new instance of fragment MyCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCourseFragment newInstance(User user) {
        MyCourseFragment fragment = new MyCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        final Activity activity2 = getActivity();
        //userid = (String)getArguments().get("userid");


        myCourselistview = (ListView)activity2.findViewById(R.id.MyCourseList);
        //loadJSON();
        //loaducourseJSON();
        SimpleAdapter adapter2 = new SimpleAdapter(
                activity2,
                listitem,
                R.layout.list_item,
                new String[]{"image_expense","description","name"},
                new int[]{R.id.cover,R.id.introduction,R.id.name});

        myCourselistview.setAdapter(adapter2);

        myCourselistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String courseid = CourseList2.get(position).getCourseid();
                String name = CourseList2.get(position).getCoursename();
                String code = CourseList2.get(position).getCode();
                String openDate = CourseList2.get(position).getRemindtime();
                String description=CourseList2.get(position).getDescription();
                bundle.putString("courseid", courseid);
                bundle.putString("name", name);
                bundle.putString("code", code);
                bundle.putString("openDate", openDate);
                bundle.putString("description", description);

                Intent intent = new Intent(activity2,Introduction.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_course, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadJSON() {
        try {
            Service request = Client.retrofit.create(Service.class);
            Call<List<Course>> call = request.getCourses();
            call.enqueue(new Callback<List<Course>>() {

                @Override
                public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                    CourseList2 = response.body();
                    loaducourseJSON();
                }

                @Override
                public void onFailure(Call<List<Course>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }

            }) ;

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void loaducourseJSON() {
        try {
            userid = (String)getArguments().get("userid");
            Service request = Client.retrofit.create(Service.class);
            Call<List<UserCourse>> call = request.getUserCourses();
            call.enqueue(new Callback<List<UserCourse>>() {

                @Override
                public void onResponse(Call<List<UserCourse>> call, Response<List<UserCourse>> response) {
                    UserCourseList = response.body();

                    for(int i = 0 ;i < UserCourseList.size(); i++){
                        if(userid.equals(UserCourseList.get(i).getUser_id())){
                            for(int j=0;j<CourseList2.size();j++){
                                if(UserCourseList.get(i).getCourse_id().equals(CourseList2.get(j).getCourseid())){
                                    Map<String, Object> map = new HashMap<String, Object>();
                                    if(CourseList2.get(j).getCourseid().equals("001")){
                                        map.put("image_expense", R.drawable.course1);
                                    }else if(CourseList2.get(j).getCourseid().equals("002")){
                                        map.put("image_expense", R.drawable.course2);
                                    }else{
                                        map.put("image_expense", R.drawable.course3);
                                    }
                                    map.put("name", CourseList2.get(j).getCoursename());
                                    map.put("description", CourseList2.get(j).getDescription());
                                    listitem.add(map);
                                }
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<UserCourse>> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                }

            }) ;

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
