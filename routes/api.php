<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/



Route::post('login', 'UserController@login');
Route::post('register', 'UserController@register');
Route::group(['middleware' => 'auth:api'], function() {
	Route::post('details', 'UserController@details');
	Route::post('update', 'UserController@update');
	Route::post('changepassword', 'UserController@change');
	Route::post('logout', 'UserController@logout');
	Route::resource('profiles','ProfilesController');
	Route::resource('products','ProductsController');
	Route::get('search','ProductsController@search');
/*	Route::get('file','FileController@user');
	Route::post('file','FileController@upload');*/
});
Route::post('create', 'ResetPasswordController@sendMail');
Route::get('find/{token}', 'ResetPasswordController@find');
Route::post('reset', 'ResetPasswordController@reset');
