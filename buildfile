require 'buildr/git_auto_version'
require 'buildr/top_level_generate_dir'
require 'buildr/gwt'

desc 'React4j Flux Challenge implementation'
define 'react4j-sithtracker' do
  project.group = 'org.realityforge.react4j.sithtracker'
  compile.options.source = '17'
  compile.options.target = '17'
  compile.options.lint = 'all,-processing,-serial'
  project.compile.options.warnings = true
  project.compile.options.other = %w(-Werror -Xmaxerrs 10000 -Xmaxwarns 10000)

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  compile.with :javax_annotation,
               :jetbrains_annotations,
               :jsinterop_base,
               :jsinterop_annotations,
               :akasha,
               :braincheck_core,
               :braincheck_jre,
               :grim_annotations,
               :zemeckis,
               :react4j_core,
               :react4j_dom,
               :arez_core,
               :arez_spytools,
               :sting_core,
               :gwt_user

  compile.options[:processor_path] << [:react4j_processor, :arez_processor, :sting_processor]

  # Exclude the Dev module if EXCLUDE_GWT_DEV_MODULE is true
  GWT_MODULES = %w(react4j.sithtracker.SithTrackerProd) + (ENV['EXCLUDE_GWT_DEV_MODULE'] == 'true' ? [] : %w(react4j.sithtracker.SithTrackerDev))
  gwt_enhance(project,
              :modules_complete => true,
              :package_jars => false,
              :gwt_modules => GWT_MODULES,
              :module_gwtc_args => {
                'react4j.sithtracker.SithTrackerDev' => %w(-optimize 9 -checkAssertions -XmethodNameDisplayMode FULL -noincremental),
                'react4j.sithtracker.SithTrackerProd' => %w(-XdisableClassMetadata -XdisableCastChecking -optimize 9 -nocheckAssertions -XmethodNameDisplayMode NONE -noincremental -compileReport)
              })

  iml.excluded_directories << project._('tmp')

  ipr.add_gwt_configuration(project,
                            :gwt_module => 'react4j.sithtracker.SithTrackerDev',
                            :start_javascript_debugger => false,
                            :open_in_browser => false,
                            :vm_parameters => '-Xmx2G',
                            :shell_parameters => "-strict -style PRETTY -XmethodNameDisplayMode FULL -nostartServer -incremental -codeServerPort 8889 -bindAddress 0.0.0.0 -deploy #{_(:generated, :gwt, 'deploy')} -extra #{_(:generated, :gwt, 'extra')} -war #{_(:generated, :gwt, 'war')}",
                            :launch_page => "http://127.0.0.1:8889/sithtracker_dev/index.html")

  ipr.add_component_from_artifact(:idea_codestyle)
  ipr.add_code_insight_settings
  ipr.add_nullable_manager
  ipr.add_javac_settings('-Xlint:all,-processing,-serial -Werror -Xmaxerrs 10000 -Xmaxwarns 10000')
end
