<?php
Class Enquiry_model extends CI_Model
{
	function __construct()
	{
		parent::__construct();
	}
	

	
	function get_enquiry($provid)
	{
		$this->db->select();
		$this->db->join('tbl_projects', 'tbl_projects.projid = tbl_provider_enquires.project_id');
		$this->db->join('tbl_msiteuser', 'tbl_msiteuser.isiteuser = tbl_provider_enquires.student_id');			
		$this->db->where('provider_id', $provid);
		$this->db->limit(5);
		
		return $this->db->get('tbl_provider_enquires')->result();
		//die;
	}
	
	function count_enquiry()
	{
		return $this->db->count_all_results('tbl_enquires');
	}	
	

	function get_enquires_list($id)
	{
		$this->db->join('tbl_trips', 'tbl_trips.trip_id = tbl_enquires.enquiry_trip_id');
		$this->db->join('tbl_users', 'tbl_users.user_id = tbl_enquires.enquiry_passanger_id');
		$this->db->join('tbl_vehicle','tbl_vehicle.vechicle_id = tbl_trips.trip_vehicle_id');
		$this->db->join('tbl_vechicle_types','tbl_vechicle_types.vechicle_type_id = tbl_vehicle.vechicle_type_id ');
		$this->db->where('tbl_enquires.enquire_travel_id', $id);
                $this->db->where('(CURDATE() <= tbl_enquires.enquiry_trip_date OR tbl_enquires.enquiry_trip_status = 1)');
//                $this->db->get('tbl_enquires');
		return $this->db->get('tbl_enquires')->result();
//                echo $this->db->last_query();
//		die;
		
	}
	/*Fonction pour récupérer les passagers confrimés d'un trajet*/
	function get_passengers_in_trip($trip_id, $trip_date, $trip_led_id)
	{
		$this->db->select('*');
		$this->db->join('tbl_t_trip_legs', 'tbl_t_trip_legs.trip_led_id');
		$this->db->join('tbl_users', 'tbl_users.user_id = tbl_enquires.enquiry_passanger_id');
		$this->db->where('tbl_t_trip_legs.trip_led_id', $trip_id);
        $this->db->where('tbl_enquires.enquiry_trip_date', $trip_date);
        $this->db->where('tbl_enquires.enquiry_trip_status = 1');
        $this->db->where('tbl_enquires.enquiry_trip_id', $trip_led_id);
		return $this->db->get('tbl_enquires')->result();
	}
        
        
    function checkAvailableSeat($tripDate,$tripId,$enquiryId){
        
        $this->db->select("(SELECT trip_avilable_seat FROM tbl_trips WHERE trip_id=".$tripId.") - (SELECT IFNULL(COUNT(enquiry_id), 0) FROM tbl_enquires WHERE enquiry_trip_id=".$tripId
            . " AND enquiry_trip_date=".$tripDate. " AND enquiry_trip_status= 1) as balanceSeat",FALSE);        
        $this->db->where('tbl_enquires.enquiry_id', $enquiryId);
        $result = $this->db->get('tbl_enquires')->row();
        $balanceSeat = $result->balanceSeat;
        if($balanceSeat > 0){
            return true;
        }else{
            return false;
        }
    }
	
	
	function get_enquiries($typeid) {

        $result = $this->db->get_where('tbl_enquires', array('enquiry_id' => $typeid));
        return $result->row();
		//print_r($result); die;
    }
    
    function save_enquiry($data) {

        if ($data['enquiry_id']) {
            $this->db->where('enquiry_id', $data['enquiry_id'])->update('tbl_enquires', $data);
            return $data['enquiry_id'];
        } else {
            $this->db->insert('tbl_enquires', $data);
            return $this->db->insert_id();
        }
    }
	
	    
    function delete($prodid) {

        $this->db->where('enquiry_id', $prodid);
        $this->db->delete('tbl_enquires');
    }
		
}