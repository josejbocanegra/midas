<?php

namespace App\Http\Controllers;

use App\Location as Location;

use Illuminate\Http\Request;

class LocationController extends Controller
{
    public function locationAll(){
    	$location = Location::all(); 
    	return $location;
    }

	public function filter(){
		$location = Location::where()->get();
    	return $location;
	}
}

