<?php

namespace App\Http\Controllers;

use App\Test as Test;

use Illuminate\Http\Request;

class TestController extends Controller
{
    public function testAll(){
    	$test = Test::all(); 
    	return $test;
    }

	public function filter($device, $connection){
		$filter = Test::where('device'=$device)->where('connection'=$connection)->get();
    	return $filter;
	}
}

