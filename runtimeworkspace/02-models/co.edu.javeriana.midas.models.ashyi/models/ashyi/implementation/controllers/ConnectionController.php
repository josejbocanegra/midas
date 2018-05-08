<?php

namespace App\Http\Controllers;

use App\Connection as Connection;

use Illuminate\Http\Request;

class ConnectionController extends Controller
{
    public function connectionAll(){
    	$connection = Connection::all(); 
    	return $connection;
    }

	public function filter(){
		$connection = Connection::where()->get();
    	return $connection;
	}
}

