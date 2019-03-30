package com.crazypudding.greendaodemo;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.crazypudding.greendaodemo.greendao.dao.CompanyDao;
import com.crazypudding.greendaodemo.greendao.dao.DaoSession;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mAdapter;
    private ListAdapter mQueryAdapter;
    private CompanyDao mCompanyDao;
    private List<Company> mEmployeeList;
    private List<Company> mQueryList;
    private Button mAddButton;
    private Button mUpdateButton;
    private EditText mNameEditor;
    private EditText mSalaryEditor;
    private String mName;
    private int mSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up toolbar as actionbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));

        //get the company DAO
        DaoSession daoSession = ((MyApplication) getApplication()).getDaoSession();
        mCompanyDao = daoSession.getCompanyDao();

        //query all employee to show in the list
        mEmployeeList = mCompanyDao.queryBuilder().list();

        setupView();

        handleIntent(getIntent());
    }

    //show the queryResult in the listView
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            mQueryList = mCompanyDao.queryBuilder().where(CompanyDao.Properties.Name.eq(query)).list();

            //instantiate adapter which used to hold queryResult show in queryList
            mQueryAdapter = new ListAdapter(this, R.layout.list_item, mQueryList);
            mListView.setAdapter(mQueryAdapter);
        }
    }

    private void setupView() {

        if (mQueryList != null) {
            System.out.println("=====>   1:   " + mQueryList.toString());
        } else {
            System.out.println("=====>   1:   " + "mQueryList为空！");
        }
        if (mEmployeeList != null) {
            System.out.println("=====>   1:   " + mEmployeeList.toString());
        } else {
            System.out.println("=====>   1:   " + "mEmployeeList为空！");
        }
        //Set listView
        mListView = (ListView) findViewById(R.id.list_view);

        //instantiate adapter which used to hold data show in normal list
        mAdapter = new ListAdapter(this, R.layout.list_item, mEmployeeList);

        mListView.setEmptyView(findViewById(R.id.empty_view));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                showDeleteAlertDialog(mEmployeeList.get(i).getId());
                if (mQueryList == null) {
                    showDeleteAlertDialog(mEmployeeList.get(i).getId());
                    Log.d("TAG", "onItemClick:  当前列表点击item的ID为：   " + mEmployeeList.get(i).getId());
                } else if (mQueryList != null) {
                    Company company = mQueryList.get(i);
                    String name = company.getName();
                    Long queryId = company.getId();
                    Log.d("TAG", "onItemClick:  当前查询列表点击item的ID为：   " + queryId + "    当前姓名：   " + name);
                    showDeleteAlertDialog(queryId);
                }
            }
        });


        //bind nameEditText
        mNameEditor = (EditText) findViewById(R.id.name_edit);

        //bind salaryEditText
        mSalaryEditor = (EditText) findViewById(R.id.salary_edit);

        //bind addButton and add a employee when addButton pressed
        mAddButton = (Button) findViewById(R.id.add_bt);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployee();
            }
        });

        //bind updateButton and update a employee when updateButton pressed
        mUpdateButton = (Button) findViewById(R.id.update_bt);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee();
            }
        });
    }

    /**
     * @param id id of employee in database table
     */
    private void showDeleteAlertDialog(final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_alert);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteEmployee(id);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.create().show();
    }

    //add a employee by a name and the salary
    private void addEmployee() {
        getValue();

        if (mName.equals("")) {
            mNameEditor.setError(getString(R.string.name_required));
            return;
        }
        Company employee = new Company(null, mName, mSalary);
        mCompanyDao.insert(employee);
        cleanEditText();
        refreshList();
    }

    //get value from the EditText
    private void getValue() {
        mName = mNameEditor.getText().toString().trim();
        String salaryString = mSalaryEditor.getText().toString().trim();
        if (!salaryString.equals("")) {
            mSalary = Integer.parseInt(salaryString);
        } else {
            mSalary = 0;
        }
    }

    //set EditText to null
    private void cleanEditText() {
        mNameEditor.setText("");
        mSalaryEditor.setText("");
        if (mQueryList != null) {
            mQueryList.clear();
            mQueryList = null;
        }
    }

    //refresh the listView to show latest data
    private void refreshList() {
        if (mQueryList != null) {
            mQueryList.clear();
            mQueryList = null;
        }
        mEmployeeList.clear();
        mEmployeeList.addAll(mCompanyDao.queryBuilder().list());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * delete a employee by id from the database table
     *
     * @param id id of employee in database table
     */
    private void deleteEmployee(long id) {
        Log.d("TAG", "deleteEmployee:  当前ID为：   " + id);
        mCompanyDao.deleteByKey(id);
        refreshList();
    }

    //update a employee by his/her name
    private void updateEmployee() {

        getValue();

        if (mName.equals("")) {
            mNameEditor.setError(getString(R.string.name_required));
            return;
        }

        Company employee = mCompanyDao.queryBuilder().where(CompanyDao.Properties.Name.eq(mName)).build().unique();
        if (employee != null) {
            employee.setSalary(mSalary);
            mCompanyDao.update(employee);
            cleanEditText();
            refreshList();
            Toast.makeText(MainActivity.this, R.string.update_succeed, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.no_employee, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        //set searchView configuration to search something
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //show appropriate employee when query text changed
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.equals("")) {
                    mListView.setAdapter(mAdapter);
                    refreshList();
                    return true;
                } else {
                    if (mQueryList != null) {
                        System.out.println("=====>   2:   " + mQueryList.toString());
                    } else {
                        System.out.println("=====>   2:   " + "mQueryList为空！");
                    }
                    if (mEmployeeList != null) {
                        System.out.println("=====>   2:   " + mEmployeeList.toString());
                    } else {
                        System.out.println("=====>   2:   " + "mEmployeeList为空！");
                    }
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")) {
                    mListView.setAdapter(mAdapter);
                    refreshList();
                    return true;
                } else {
                    return false;
                }
            }
        });

        //show all employee in the list when searchView collapse
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mListView.setAdapter(mAdapter);
                refreshList();
                return true;
            }
        });

        return true;
    }
}