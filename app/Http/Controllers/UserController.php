<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request; 
use App\Http\Controllers\Controller; 
use App\User; 
use Illuminate\Support\Facades\Auth; 
use Illuminate\Support\Facades\Route;
use Laravel\Passport\Client;
use Illuminate\Support\Facades\Hash;
use Validator;

class UserController extends Controller 
{
	public $successStatus = 200;
/** 
     * login api 
     * 
     * @return \Illuminate\Http\Response 
     */ 
    public function login(){ 
        if(Auth::attempt(['email' => request('email'), 'password' => request('password')])){ 
            $user = Auth::user(); 
            $success['token'] =  $user->createToken('MyApp')-> accessToken; 
            return response()->json(['success' => $success], $this-> successStatus); 
        } 
        else{ 
            return response()->json(['error'=>'Unauthorised'], 401); 
        } 
    }
/** 
     * Register api 
     * 
     * @return \Illuminate\Http\Response 
     */ 
    public function register(Request $request) 
    { 
        $validator = Validator::make($request->all(), [ 
            'name' => 'required', 
            'email' => 'required|unique:users', 
            'password' => 'required|min:6|confirmed', 
        ]);
		if ($validator->fails()) { 
            return response()->json(['error'=>$validator->errors()], 401);            
        }
		$input = $request->all(); 
        $input['password'] = bcrypt($input['password']); 
        $user = User::create($input); 
/*        $success['token'] =  $user->createToken('MyApp')-> accessToken;*/ 
        $success['name'] =  $user->name;
        $success['email'] =  $user->email;
		return response()->json(['message'=>'success','success'=>$success], $this-> successStatus); 
    }
/** 
     * details api 
     * 
     * @return \Illuminate\Http\Response 
     */ 
    public function details() 
    { 
        $user = Auth::user(); 
        return response()->json(['success' => $user], $this-> successStatus); 
    } 

    public function update(Request $request)
    {
        $user = Auth::user();       
        $user->update($request->except('password'));

        return response()->json(['message' => 'Update Successful'], $this-> successStatus); 
    }

    public function change(Request $request)
    {
        $user = Auth::user();
        $data = $this->validate($request, [
            'current_password' => ['required', function($attribute, $value, $fail) {
                $user = Auth::user();
                if (!Hash::check($value, $user->password)) {
                    return $fail($attribute.' is invalid.');
                }
        }],
            'new_password' => 'required|string|min:6|different:current_password|confirmed',
        ]);
        $user->password = bcrypt($data['new_password']);
        $user->save();

        return response()->json(['message' => 'Update Successful'], $this-> successStatus); 
    }
/**
     * Log the user out (Invalidate the token).
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function logout()
    {
        Auth::user()->token()->delete();

        return response()->json(['message' => 'Logout Successful'], $this-> successStatus);
    }
}