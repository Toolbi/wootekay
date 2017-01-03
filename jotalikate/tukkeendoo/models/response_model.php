<?php
class Response_model extends CI_Model 
{
	function __construct()
	{
		parent::__construct();
	}
	
	function print_json_response($response = array())
	{
		header('Content-Type: application/json');
		print_r(json_encode($response));
	}
}
?>