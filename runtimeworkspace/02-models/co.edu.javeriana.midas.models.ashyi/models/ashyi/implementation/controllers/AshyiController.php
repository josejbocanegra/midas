<?php

namespace App\Http\Controllers;

use App\Ashyi as Ashyi;

use Illuminate\Http\Request;

class AshyiController extends Controller
{
    public function ashyiAll(){
    	$ashyi = Ashyi::all(); 
    	return $ashyi;
    }

	public function filter(){
		$ashyi = Ashyi::where()->get();
    	return $ashyi;
	}
}

