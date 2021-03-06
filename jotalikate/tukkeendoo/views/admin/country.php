<?php include('header.php'); ?>
<?php include('left.php'); ?>
<?php echo admin_js('admin.js', true); ?>
<script type="text/javascript" language="javascript">

    var baseurl = "<?php print base_url(); ?>";
    function country_ajax(url) {

        $.ajax({
            type: "POST",
            url: baseurl + "admin/country/country_ajax/" + url,
            success: function (html) {
                var message = $('<div />').append(html).find('#splitresult').html();
                $("#pageresult").html(message);
            }
        });
    }

    function areyousure()
    {
        return confirm('<?php echo lang('delete_confirm'); ?>');
    }
</script>

<div id="content-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <ol class="breadcrumb">
                <li><a href="<?php echo base_url('admin/dashboard'); ?>"><?php echo lang('admin_home'); ?></a></li>
                <li class="active"><span><?php echo lang('country2'); ?></span></li>
            </ol>

            <div class="clearfix">
                <h1 class="pull-left"><?php echo lang('all_country'); ?></h1>

                <div class="pull-right top-page-ui">
                    <a href="<?php echo base_url('admin/country/form'); ?>" class="btn btn-primary pull-right">
                        <i class="fa fa-plus-circle fa-lg"></i> <?php echo lang('add_country'); ?>
                    </a>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="main-box no-header clearfix">
                    <div class="main-box-body clearfix">
                        <div id="pageresult">
                            <div class="table-responsive">                                             
                                <table class="table user-list table-hover">
                                    <thead>
                                        <tr>
                                            <th><span>Id</span></th>
                                            <th><span><?php echo lang('country_name'); ?></span></th>
                                            <th><span><?php echo lang('country_short_name'); ?></span></th>                                            
                                            <th><span><?php echo lang('created'); ?></span></th>
                                            <th class="text-center"><span><?php echo lang('status'); ?></span></th>															
                                            <th>&nbsp;</th>
                                            <th>&nbsp;</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($countries as $country): ?>
                                            <tr>
                                                <td>
                                                    <?= $country['country_id'] ?>
                                                </td>
                                                <td>
                                                    <?= $country['country_name'] ?>
                                                </td>
                                                <td>
                                                    <?= $country['country_code'] ?>
                                                </td>                                                
                                                <td>
                                                    <?php echo date('d, M Y h:i A', strtotime($country['created_date'])); ?> 

                                                </td>
                                                <td class="text-center">
                                                    <?php if ($country['is_active'] == 0) { ?>
                                                        <span class="label label-default" id="label-<?= $country['country_id'] ?>"><?php echo lang('inactive'); ?></span>
                                                    <?php } else { ?>
                                                        <span class="label label-success" id="label-<?= $country['country_id'] ?>"><?php echo lang('active'); ?></span>
                                                    <?php } ?>
                                                </td>
                                                <td class="text-center">
                                                    <div class="btn-group" id="btn-<?= $country['country_id'] ?>">
                                                        <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
                                                            <?php echo lang('change_status'); ?> <span class="caret"></span>
                                                        </button>
                                                        <ul class="dropdown-menu" role="menu">
                                                            <?php if ($country['is_active'] == 0) { ?>
                                                                <li style="border:0px; height:auto; padding:0px;"><a href="#" id="enable-<?= $country['country_id'] ?>" class="change-status-country" rel="<?= $country['country_id'] ?>"><?php echo lang('enable'); ?></a></li>
                                                            <?php } else { ?>
                                                                <li style="border:0px; height:auto; padding:0px;"><a href="#" id="disable-<?= $country['country_id'] ?>" class="change-status-country" rel="<?= $country['country_id'] ?>"><?php echo lang('disable'); ?></a></li>
                                                            <?php } ?>

                                                            <li class="divider"></li>
                                                        </ul>

                                                    </div>
                                                </td>
                                                <td style="width: 20%;" class="bacis">
                                                    
                                                    <a href="<?php echo base_url('admin/country/form/' . $country['country_id']); ?>" class="table-link">
                                                        <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-pencil fa-stack-1x fa-inverse"></i>
                                                        </span>
                                                    </a>
                                                    <a href="<?php echo base_url('admin/country/delete/' . $country['country_id']); ?>"  onclick="return areyousure();" class="table-link danger">
                                            <span class="fa-stack">
                                                            <i class="fa fa-square fa-stack-2x"></i>
                                                            <i class="fa fa-trash-o fa-stack-1x fa-inverse"></i>
                                                        </span>                                               </a>
                                                    
                                                </td>
                                            </tr>
                                        <?php endforeach; ?>
                                    </tbody>
                                </table>
                            </div>
                            <?php echo $pagination ?>
                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
    <script>
	 $(document).ready(function() {
                   $('body').find('.bacis a').popover();
            });
	</script>


    <?php include('footer.php'); ?>
