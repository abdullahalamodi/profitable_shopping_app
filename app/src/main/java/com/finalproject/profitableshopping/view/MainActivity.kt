package com.finalproject.profitableshopping.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.finalproject.profitableshopping.R
import com.finalproject.profitableshopping.view.authentication.fragments.LogInFragment
import com.finalproject.profitableshopping.view.authentication.fragments.SignUpFragment
import com.finalproject.profitableshopping.data.models.Product
import kotlinx.android.synthetic.main.activity_main.*
import com.finalproject.profitableshopping.view.category.CategoryFragment
import com.finalproject.profitableshopping.view.products.fragments.AddProductFragment
import com.finalproject.profitableshopping.view.products.fragments.ProductDetailsFragment
import com.finalproject.profitableshopping.view.products.fragments.ProductListFragment

class MainActivity : AppCompatActivity(), ProductListFragment.Callbacks,
    AddProductFragment.Callbacks,
ProductDetailsFragment.Callbacks{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //hide app bar elevation
        supportActionBar?.elevation = 0.0f


        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, ProductListFragment.newInstance())
                .commit()
        }


        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setContent("Home")
                    setCurrentFragment(ProductListFragment.newInstance())
                    true
                }
                R.id.menu_shopping_cart -> {
//                    setContent("Cart")
//                    setCurrentFragment(productListFragment)
                    true
                }
                R.id.menu_search -> {
//                    setContent("Search")
                    true
                }
                R.id.menu_notification -> {
                    setContent("Profile")
                    true
                }
                R.id.menu_profile -> {
                    setContent("Profile")
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()

        }

    private fun setContent(content: String) {
        supportActionBar?.title = content;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar

        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        if(getUserToken()=="Admin"){
                menu.findItem(R.id.menu_add_product).setVisible(false)
                menu.findItem(R.id.menu_my_products).setVisible(false)
            menu.findItem(R.id.menu_login).setVisible(false)
            }
        else if(getUserToken() ==null || getUserToken() =="") {
            menu.findItem(R.id.menu_add_product).setVisible(false)
            menu.findItem(R.id.menu_my_products).setVisible(false)
            menu.findItem(R.id.menu_categories).setVisible(false)
            menu.findItem(R.id.sign_out).setVisible(false)
            menu.findItem(R.id.menu_settings).setVisible(false)
        }
        else {
            menu.findItem(R.id.menu_categories).setVisible(false)
            menu.findItem(R.id.menu_login).setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_product -> {
                if (getUserToken() != null)
                    setCurrentFragment(AddProductFragment.newInstance(null))
                else {
                    /*val intent = Intent(this, SignIn::class.java).apply {
//                        putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)*/
                    setCurrentFragment(LogInFragment.newInstance())
                }
                true
            }
            R.id.menu_login -> {
               /* val intent = Intent(this, SignIn::class.java).apply {
//                        putExtra(EXTRA_MESSAGE, message)
                }
                startActivity(intent)*/
                setCurrentFragment(LogInFragment.newInstance())
                true
            }
            R.id.menu_my_products -> {
                if (getUserToken() != null)
                    setCurrentFragment(ProductListFragment.newInstance())
                else {
                    /*val intent = Intent(this, SignIn::class.java).apply {
//                        putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)*/
                    setCurrentFragment(LogInFragment.newInstance())
                }
                true
            }
            R.id.menu_categories -> {
                if (getUserToken() != null && getUserToken() == "Admin")
                    setCurrentFragment(CategoryFragment.newInstance())
                else if (getUserToken() != "Admin") {
                    Toast.makeText(this, "yor are not admin", Toast.LENGTH_SHORT).show()
                } else {
                   /* val intent = Intent(this, SignIn::class.java).apply {
//                        putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)*/
                    setCurrentFragment(LogInFragment.newInstance())
                }
                true
            }
            R.id.sign_out ->{
                logOut()
                setCurrentFragment(LogInFragment.newInstance())
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



    private fun getUserToken(): String? {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            LogInFragment.sharedPrefFile,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(LogInFragment.tokenKey, null)
    }

  fun logOut(token: String =""){
      val sharedPreferences: SharedPreferences = this.getSharedPreferences(
          LogInFragment.sharedPrefFile,
          Context.MODE_PRIVATE
      )
      val editor: SharedPreferences.Editor = sharedPreferences.edit()
      editor.putString(LogInFragment.tokenKey, token)
      editor.apply()
      editor.commit()
  }
    private fun addFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, fragment)
            addToBackStack(null)
            commit()
        }
    override fun onItemSelected(itemId: Int) {
        addFragment(ProductDetailsFragment.newInstance(itemId.toString()))
    }
    override fun onSuccessAddProduct() {
        setCurrentFragment(ProductListFragment.newInstance())
    }
    override fun onSignUpClicked() {
        setCurrentFragment(SignUpFragment.newInstance())
    }

    override fun onCreateAccountSuccess() {
        setCurrentFragment(SignUpFragment.newInstance())
    }

    companion object {
        private const val sharedPrefFile = "user_pref";
        private var tokenKey = "token_key";

    }


    override fun onUpdateProductClicked(productId: String?) {
        setCurrentFragment(AddProductFragment.newInstance(productId!!))
    }


}