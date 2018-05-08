<?php

namespace App\Http\Controllers;

use App\Personality as Personality;

use Illuminate\Http\Request;

class PersonalityController extends Controller
{
    public function personalityAll(){
    	$personality = Personality::all(); 
    	return $personality;
    }

	public function filter(){
		$personality = Personality::where()->get();
    	return $personality;
	}
}

