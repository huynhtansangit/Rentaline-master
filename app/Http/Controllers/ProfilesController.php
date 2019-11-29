<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\User; 
use App\Profiles;
use Illuminate\Support\Facades\Auth;

class ProfilesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return response()->json(Profiles::get(),200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $profile = new Profiles;
        $id = Auth::user()->id;
        if(Profiles::where('user_id', $id )->exists()){
            return response()->json(['error'=>'Duplicated'], 401);  
        }
        else{
            $profile->user_id = $id;
        }
        $profile->name = $request->input('name');
        $profile->birth_day = $request->input('birth_day');
        $profile->phone = $request->input('phone');
        $profile->address = $request->input('address');
        if($request->hasFile('photo')){
            $fileName = $request->file('photo')->getClientOriginalName();
            $path = $request->file('photo')->move(public_path('upload/'),$fileName);
            $photoURL = url('upload/'.$fileName); 
            $profile->avatar = $photoURL;  
        }
        $profile->save();
        return response()->json($profile,201);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $profile = Profiles::find($id);       
        $profile->update($request->all());

        return response()->json(['message' => 'Update Successful'], 201);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
