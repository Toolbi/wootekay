<?php include('header.php'); ?>
<?php include('left.php'); ?>
<?php echo admin_js('admin.js', true); ?>
<script type="text/javascript" language="javascript">

    var baseurl = "<?php print base_url(); ?>";
    function language_ajax(url) {

        $.ajax({
            type: "POST",
            url: baseurl + "admin/language/language_ajax/" + url,
            success: function (html) {
                var message = $('<div />').append(html).find('#splitresult').html();
                $("#pageresult").html(message);
            }
        });
    }

    function areyousure()
    {
        return confirm('<?php echo lang('delete_confirm');?>');
    }
</script>

<div id="content-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <ol class="breadcrumb">
                <li><a href="<?php echo base_url('admin/dashboard'); ?>"><?php echo lang('admin_home'); ?></a></li>
                <li class="active"><span><?php echo lang('language');?></span></li>
            </ol>

            <div class="clearfix">
                <h1 class="pull-left">
                    <?php if(!empty($page_title)):?>
                    <?php echo  $page_title; ?>
                    <?php endif; ?>
                </h1>

                <div class="pull-right top-page-ui">
                    <a href="<?php echo base_url('admin/language/form'); ?>" class="btn btn-primary pull-right">
                        <i class="fa fa-plus-circle fa-lg"></i> <?php echo lang('add_language');?>
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
                                            <th><span><?php echo lang('language_name');?></span></th>
                                            <th><span><?php echo lang('language_prefix');?></span></th>
                                            <th><span><?php echo lang('created');?></span></th>                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <?php foreach ($languages as $language): ?>
                                            <tr>
                                                <td>
                                                    <?= $language['language_id'] ?>
                                                </td>
                                                <td>
                                                    <?= $language['language_name'] ?>
                                                </td>
                                                <td>
                                                    <?= $language['language_code'] ?>
                                                </td>
                                                <td>
                                                    <?php echo date('d, M Y h:i A', strtotime($language['created_date'])); ?> 

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


    <?php include('footer.php'); ?>
