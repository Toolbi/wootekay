<?php

class Profile extends Traveller_Controller 
{

    var $CI;
    var $user_id;
	var $user;
	
    function __construct() 
	{
        parent::__construct();

        $this->CI = & get_instance();

        $this->load->helper('form');
        $this->load->helper('date');
        $this->load->model('Customer_model');
        $this->load->model('vechicle_model');
        $this->load->model('response_model');
        $this->load->model('trip_model');
        $this->lang->load('backend');
        $this->load->helper('url');
		
        $this->user = $this->CI->carpool_session->userdata('carpool');
		$this->user_id = $this->user['user_id'];
    }

    function index() 
	{
		$this->dispatch();
        //$carpool_session['carpool_session'] = $this->CI->carpool_session->userdata('carpool');
        //$id = $this->user['user_id'];
        //$data['customer'] = $this->Customer_model->get_customer($id);
        //$data['user_vehicles'] = $this->vechicle_model->getvechicle_list($id);

/*
        $data['seo_title'] = 'Edit - Company Details';
        $data['seo_description'] = '';
        $data['seo keyword'] = '';


        $data['number'] = '';
        $data['sms'] = '';

        $data['firstname'] = '';
        $data['lastname'] = '';
        $data['email'] = '';
        $data['phone'] = '';
        $data['gender'] = '';
        $data['email_2'] = '';
        $data['aphone'] = '';
        $data['mobile_flg'] = '';
        $data['mail_flg'] = '';
        $data['street'] = '';
        $data['city'] = '';
        $data['code'] = '';
        $data['country'] = '';
        $data['occupation'] = '';
        $data['month'] = '';
        $data['selday'] = '';
        $data['year'] = '';
        $data['martial_status'] = '';
        
        $data['cmpname'] = '';
        $data['url'] = '';
        $data['number'] = '';
        $data['sms'] = '';
        $data['food'] = '';
        $data['pet'] = '';
        $data['smoke'] = '';
        $data['chat'] = '';
        $data['music'] = '';
     
	 

        $data['redirect'] = '';
*/
/*
        if ($id) 
		{

            $profile = $this->Customer_model->get_customer($id);
            if (!$profile) 
			{
                $this->session->set_flashdata('error', lang('error_not_found'));
                redirect('dispatcher');
            }


			//print_r($profile);
            //set values to db values
            $userProfile['id'] = $id;
            $userProfile['firstname'] = $profile->user_first_name;
            $userProfile['lastname'] = $profile->user_last_name;
            $userProfile['phone'] = $profile->user_mobile;
            $userProfile['email'] = $profile->user_email;
            $userProfile['gender'] = $profile->user_gender;
            $userProfile['mail_flg'] = $profile->communication_email;
            $userProfile['aboutus'] = $profile->user_about_us;
            $userProfile['email_2'] = $profile->user_secondary_mail;
            $userProfile['number'] = $profile->show_number;
            $userProfile['sms'] = $profile->send_sms;
            $userProfile['food'] = $profile->allowed_food;
            $userProfile['pet'] = $profile->allowed_pet;
            $userProfile['smoke'] = $profile->allowed_smoke;
            $userProfile['chat'] = $profile->allowed_chat;
            $userProfile['music'] = $profile->allowed_music;

            if (!empty($profile->user_dob)) 
			{
                //$dob = explode("-", $profile->user_dob);
				//print_r($profile->user_dob);
                //$userProfile['month'] = date("F", strtotime($profile->user_dob));
                //$userProfile['year'] = $dob[0];
                //$userProfile['day'] = $dob[2];
                $userProfile['dob'] = $profile->user_dob;
            }
			$data['user_profile'] = $userProfile;
        }
		
		$response['user'] = $data;
		$this->response_model->print_json_response($response);
        //$this->load->view('profile', $data);
 * 
 */
    }

	function dispatch()
	{
		$this->session->set_flashdata('error', lang('error_not_found'));
        redirect('dispatcher');
	}
	
	function isConnected()
	{
		if (!$this->user_id) 
		{
            $this->dispatch();
		}
		return TRUE;
	}
	
	function get_user_profile() 
	{
		if ($this->isConnected()) {
			$profile = $this->Customer_model->get_customer($this->user_id);
            if ($profile) 
			{

				//set values to db values
	            $userProfile['id'] = $this->user_id;
	            $userProfile['firstname'] = $profile->user_first_name;
	            $userProfile['lastname'] = $profile->user_last_name;
	            $userProfile['phone'] = $profile->user_mobile;
	            $userProfile['email'] = $profile->user_email;
	            $userProfile['gender'] = $profile->user_gender;
	            $userProfile['mail_flg'] = $profile->communication_email;
	            $userProfile['aboutus'] = $profile->user_about_us;
	            $userProfile['email_2'] = $profile->user_secondary_mail;
	            $userProfile['number'] = $profile->show_number;
	            $userProfile['sms'] = $profile->send_sms;
	            $userProfile['food'] = $profile->allowed_food;
	            $userProfile['pet'] = $profile->allowed_pet;
	            $userProfile['smoke'] = $profile->allowed_smoke;
	            $userProfile['chat'] = $profile->allowed_chat;
	            $userProfile['music'] = $profile->allowed_music;
	
	            if (!empty($profile->user_dob)) 
				{
	                $userProfile['dob'] = $profile->user_dob;
	            }
				$response['user_profile'] = $userProfile;
				return $this->response_model->print_json_response($response);
			} 
			$this->dispatch();
		}
	}
	
	function get_user_vehicles() 
	{
		if ($this->isConnected()) {
			$vehicles = $this->vechicle_model->getvechicle_list($this->user_id);
			if ($vehicles) {
				$response['user_vehicles'] = $vehicles;
				return $this->response_model->print_json_response($response);
			} 
			$this->dispatch();
		}		
	}
	function get_user_trips() 
	{
		//TODO implement this method
		if ($this->isConnected()) {
			$trips = $this->trip_model->get_user_trips($this->user_id, 2);
			if ($trips) {
				//print_r($trips);
				foreach ($trips as $trip) {
					//print_r($trip);
				$user_trip['id'] = $trip['trip_id']; 
				$user_trip['vehicle_id'] = $trip['trip_vehicle_id']; 
				$user_trip['from_latlan'] = $trip['trip_from_latlan'];
				$user_trip['to_latlan'] = $trip['trip_to_latlan']; 
				$user_trip['source'] = $trip['source']; 
				$user_trip['destination'] = $trip['destination']; 
				$user_trip['route'] = $trip['route']; 
				$user_trip['routes_lat_lan'] = $trip['trip_routes_lat_lan']; 
				$user_trip['routes'] = $trip['trip_routes']; 
				$user_trip['return'] = isset($trip['trip_return']) && !($trip['trip_return'] == 'no'); 
				$user_trip['depature_time'] = $trip['trip_depature_time']; 
				$user_trip['return_time'] = $trip['trip_return_time']; 
				$user_trip['frequncy'] = $trip['trip_frequncy']; 
				$user_trip['available_seat'] = $trip['trip_available_seat']; 
				$user_trip['passenger_type'] = $trip['passenger_type']; 
				$user_trip['rate_details'] = $trip['trip_rate_details']; 
				$user_trip['user_id'] = $trip['trip_user_id']; 
				$user_trip['casual_date'] = $trip['trip_casual_date']; 
				$user_trip['created_date'] = $trip['trip_created_date']; 
				$user_trip['status'] = $trip['trip_status']; 
				$user_trip['public'] = $trip['trip_public']; 
				$user_trip['otp_status'] = $trip['trip_otp_status']; 
				$user_trip['flexibility'] = $trip['flexibility']; 
				$user_trip['detour'] = $trip['detour']; 
				$user_trip['luggage_size'] = $trip['luggage_size'];
				
				$user_trips[] = $user_trip;
			
				//print_r($user_trip);
			}
					
				$response['user_trips'] = $user_trips;
				
				return $this->response_model->print_json_response($response);
			}
			$this->dispatch(); 
		}
	}
	
	function get_user_messages() 
	{
		//TODO implement this method
		if ($this->isConnected()) {
			$vehicles = $this->vechicle_model->getvechicle_list($this->user_id);
			if ($vehicles) {
				$response['user_vehicles'] = $vehicles;
				return $this->response_model->print_json_response($response);
			} 
			$this->dispatch();
		}
	}
	
    function edit($id = false, $duplicate = false) 
	{
        $this->user_id = $id;
        $data['customer'] = $this->Customer_model->get_customer($this->user_id);

        $data['firstname'] = '';
        $data['lastname'] = '';
        $data['email'] = '';
        $data['phone'] = '';
        $data['gender'] = '';
        $data['email_2'] = '';
        $data['aphone'] = '';
        $data['mobile_flg'] = '';
        $data['mail_flg'] = '';
        $data['street'] = '';
        $data['city'] = '';
        $data['food'] = '';
        $data['pet'] = '';
        $data['smoke'] = '';
        $data['chat'] = '';
        $data['music'] = '';

        $data['month'] = '';
        $data['selday'] = '';
        $data['year'] = '';

        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('', '');
		
		

        $data['redirect'] = '';
        if ($this->user_id) 
		{

            $profile = $this->Customer_model->get_customer($this->user_id);
            if (!$profile) 
			{
                $this->session->set_flashdata('error', lang('error_not_found'));
                redirect('profile');
            }


            //set values to db values
            $data['id'] = $id;
            $data['firstname'] = $profile->user_first_name;
            $data['lastname'] = $profile->user_last_name;
            $data['phone'] = $profile->user_mobile;
            $data['email'] = $profile->user_email;
            $data['gender'] = $profile->user_gender;
            $data['mail_flg'] = $profile->communication_email;
            $data['aboutus'] = $profile->user_about_us;
            $data['email_2'] = $profile->user_secondary_mail;
            $data['number'] = $profile->show_number;
            $data['sms'] = $profile->send_sms;
            $data['food'] = $profile->allowed_food;
            $data['pet'] = $profile->allowed_pet;
            $data['smoke'] = $profile->allowed_smoke;
            $data['chat'] = $profile->allowed_chat;
            $data['music'] = $profile->allowed_music;

            if (!empty($profile->user_dob)) 
			{
                $dob = explode("-", $profile->user_dob);
                //print_r($dob);
                //die;
                $data['month'] = date("F", strtotime($profile->user_dob));
                $data['year'] = $dob[0];
                $data['selday'] = $dob[2];
            }


            if ($this->input->post('mail_flg') == 1) 
			{
                $this->form_validation->set_rules('email_2', 'Official Email', 'trim|required|max_length[32]');
            }
        }

        $this->form_validation->set_rules('firstname', 'Firstname', 'trim|required|min_length[3]|max_length[128]');
        $this->form_validation->set_rules('lastname', 'Lastname', 'trim|required|min_length[3]|max_length[128]');

        $this->form_validation->set_rules('phone', 'Primary mobile', 'trim|required|max_length[15]|min_length[3]|numeric');
		$this->form_validation->set_rules('day', 'D.O.B', 'required');
		$this->form_validation->set_rules('month', 'D.O.B', 'required');
		$this->form_validation->set_rules('year', 'D.O.B', 'required');
		



        if ($duplicate) 
		{
            $data['id'] = false;
        }


        if ($this->form_validation->run() == FALSE) 
		{

            //$this->load->view('profile', $data);
        } 
		else 
		{
            $this->load->helper('text');



            if ($this->input->post('year') && $this->input->post('month') && $this->input->post('day')) 
			{
                $enrolled = sprintf('%d-%02d-%02d', $this->input->post('year'), date("m", strtotime($this->input->post('month'))), $this->input->post('day'));
            } 
			else 
			{
                $enrolled = '';
            }

            $save['user_id'] = $this->user_id;
            $save['user_first_name'] = $this->input->post('firstname');
            $save['user_last_name'] = $this->input->post('lastname');
            if (empty($profile->user_email))
			 {
                $save['user_email'] = $this->input->post('email');
            }
           
            $save['user_mobile'] = $this->input->post('phone');
            $save['user_secondary_mail'] = $this->input->post('email_2');
            $save['user_gender'] = $this->input->post('gender');
            $save['communication_email'] = $this->input->post('mail_flg');
            $save['user_dob'] = $enrolled;
            $save['user_about_us'] = $this->input->post('aboutus');




            // save password 
            $profile_id = $this->Customer_model->save($save);



            $this->session->set_flashdata('message', lang('profile_update_msg'));
           // redirect('profile#personal-infos');
        }
    }

    function changepwd_form($id = false, $duplicate = false) 
	{
        $this->user_id = $id;

        if (!$id) 
		{
            $carpool_session['carpool_session'] = $this->CI->carpool_session->userdata('carpool');
            $this->user_id = $carpool_session['carpool_session']['user_id'];
        }


        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('', '');

        $data['redirect'] = '';
        if ($this->user_id) 
		{

            $profile = $this->Customer_model->get_customer($this->user_id);
            if (!$profile) 
			{

                $this->session->set_flashdata('error', lang('error_not_found'));
                redirect('profile/change_password');
            }


            //set values to db values
            $data['id'] = $this->user_id;

            //make sure we haven't submitted the form yet before we pull in the images/related products from the database
        }

        $this->form_validation->set_rules("newpwd", "New Password", "trim|required|matches[cnewpwd]");
        $this->form_validation->set_rules("oldpwd", "Old Password", "trim|required|callback_passwordCheck");


        if ($duplicate) 
		{
            $data['id'] = false;
        }


        if ($this->form_validation->run() == FALSE) 
		{

            $this->load->view('change_password', $data);
        } 
		else 
		{
            $this->load->helper('text');
			
            $save['user_id'] = $this->user_id;
            $save['user_password '] = sha1($this->input->post('newpwd'));

            // save password 
            $profile_id = $this->Customer_model->save($save);

            $this->session->set_flashdata('message', lang('pass_update_msg'));

            redirect('profile/changepwd_form');
        }
    }

    function profile_image_upload() 
	{

        $carpool_session['carpool_session'] = $this->CI->carpool_session->userdata('carpool');
        $id = $carpool_session['carpool_session']['user_id'];


        $filename = $_FILES['profileimg']['name'];
        $size = $_FILES['profileimg']['size'];


        //get the extension of the file in a lower case format
        $ext = $this->getExtension($filename);
        $ext = strtolower($ext);
        $actual_image_name = 'user' . $id . '_profile_' . time() . "." . $ext;

        //config image upload  
        $config['allowed_types'] = $this->config->item('acceptable_files');
        $config['upload_path'] = $this->config->item('profile_upload_dir') . 'original';
        $config['file_name'] = $actual_image_name;
        $config['remove_spaces'] = true;


        $this->load->library('upload', $config);

        if ($this->upload->do_upload('profileimg')) 
		{
            $upload_data = $this->upload->data();


            $this->load->library('image_lib');
            //this is the larger image
            $config['image_library'] = 'gd2';
            $config['source_image'] = $this->config->item('profile_upload_dir') . 'original/' . $upload_data['file_name'];
            $config['new_image'] = $this->config->item('profile_upload_dir') . 'source/' . $upload_data['file_name'];
            $config['maintain_ratio'] = TRUE;
            $config['width'] = 160;
            $config['height'] = 204;
            $this->image_lib->initialize($config);
            $this->image_lib->resize();
            $this->image_lib->clear();

            $data['file_name'] = $upload_data['file_name'];


            $param['user_profile_img'] = $data['file_name'];
            $param['user_id'] = $id;
            $uid = $this->Customer_model->save($param);
            if ($uid) 
			{

                echo '<img src="' . theme_profile_img($upload_data['file_name']) . '"  width="100" height="100"  style="top:0px"/>';
            }
        }

        if ($this->upload->display_errors() != '') 
		{
            echo $this->upload->display_errors();
        }
    }

    function getExtension($str) 
	{
        $i = strrpos($str, ".");
        if (!$i) 
		{
            return "";
        }

        $l = strlen($str) - $i;
        $ext = substr($str, $i + 1, $l);
        return $ext;
    }

    function settings($id = false) 
	{
        $this->user_id = $id;

        if (!$id) 
		{
            $carpool_session['carpool_session'] = $this->CI->carpool_session->userdata('carpool');
            $this->user_id = $carpool_session['carpool_session']['user_id'];
        }


        $this->load->helper('form');
        $this->load->library('form_validation');
        $this->form_validation->set_error_delimiters('', '');

        $data['redirect'] = '';
        if ($this->user_id) 
		{

            $profile = $this->Customer_model->get_customer($this->user_id);
            if (!$profile) 
			{

                $this->session->set_flashdata('error', lang('error_not_found'));
                redirect('profile#settings');
            }


            //set values to db values
            $data['id'] = $this->user_id;
            $data['number'] = $profile->show_number;
            $data['sms'] = $profile->send_sms;
            $data['food'] = $profile->allowed_food;
            $data['pet'] = $profile->allowed_pet;
            $data['smoke'] = $profile->allowed_smoke;
            $data['chat'] = $profile->allowed_chat;
            $data['music'] = $profile->allowed_music;
            //make sure we haven't submitted the form yet before we pull in the images/related products from the database
        }

        $this->form_validation->set_rules("number", "Show Number", "trim");
        $this->form_validation->set_rules("sms", "Send Sms", "trim");


        if ($this->form_validation->run() == FALSE) 
		{
            $this->session->set_flashdata('error', validation_errors());
            redirect('profile#settings');
        } 
		else 
		{
            $this->load->helper('text');

            $save['user_id'] = $this->user_id;
            $save['show_number '] = $this->input->post('number');
            $save['send_sms '] = $this->input->post('sms');
            $save['allowed_food'] = $this->input->post('food');
            $save['allowed_pet'] = $this->input->post('pet');
            $save['allowed_smoke'] = $this->input->post('smoke');
            $save['allowed_chat'] = $this->input->post('chat');
            $save['allowed_music'] = $this->input->post('music');
            //print_r($save); die();
            $profile_id = $this->Customer_model->save($save);
            //print_r($profile_id); die();
            $this->session->set_flashdata('message', lang('settings_update_msg'));

            redirect('profile#settings');
        }
    }

    function passwordCheck() 
	{
        $carpool_session['carpool_session'] = $this->CI->carpool_session->userdata('carpool');
        $this->user_id = $carpool_session['carpool_session']['user_id'];
        $md5pass = sha1($this->input->post('oldpwd'));

        $profile = $this->Customer_model->get_customer($this->user_id);
        $currentPass = $profile->user_password;

        if ($md5pass == $currentPass) 
		{
            return true;
        } 
		else 
		{
            $this->form_validation->set_message('passwordCheck', lang('pass_check_msg'));
            return false;
        }
    }

}

?>