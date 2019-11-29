<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Profiles extends Model
{
    protected $table = 'profiles';

    protected $fillable = [
    	'id',
    	'user_id',
    	'name',
    	'birth_day',
    	'phone',
    	'address',
    	'avatar',
    ];

    public function user()
    {
       return $this->belongsTo('App\User');
    }
}
