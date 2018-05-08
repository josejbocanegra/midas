<?php

namespace App\Http\Controllers;

use App\Device as Device;

use Illuminate\Http\Request;

class DeviceController extends Controller
{
    public function deviceAll(){
    	$device = Device::all(); 
    	return $device;
    }

	public function filter(){
		$device = Device::where()->get();
    	return $device;
	}
}

