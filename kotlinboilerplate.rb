#!/usr/bin/ruby -w
#
# Questo script serve per automatizzare il processo di creazione di un progetto dal progetto Boilerplate
#
# Aggiornato ad AndroidStudio 2.1.1 e Android 23
#
# © Tiknil
#

require 'optparse'
require 'fileutils'
require 'find'


############################################################################################################################################
## MAIN PROGRAM
############################################################################################################################################

## Variabili
$boilerplate_project_name = "KotlinBoilerplate"
$boilerplate_package_name = "com.tiknil.app"
$boilerplate_application_class = "KotlinBoilerplateApp.kt"
$boilerplate_fonts_class = "KotlinBoilerplateFonts.kt"
$boilerplate_constants_class = "KotlinBoilerplateConstants.kt"
$boilerplate_api_class = "KotlinBoilerplateApi.kt"
$boilerplate_description = "KotlinBoilerplate"
##

## Definizione delle opzioni che il comando ./boilerplate.rb può ricevere
options = {:project_name => nil, :package_name => nil}

parser = OptionParser.new do|opts|
	opts.banner = "Come usare: boilerplate.rb [options]"
	opts.on('-n', '--name project_name', 'Nome del progetto') do |name|
		options[:project_name] = name;
	end

	opts.on('-p', '--package package_name', 'Nome del package android (es: "' + $boilerplate_package_name + '")') do |package_name|
		options[:package_name] = package_name;
	end

	opts.on('-o', '--output_folder output_folder', 'Percorso dove si vuole inserire il nuovo progetto (es: ~/Documents)') do |output_folder|
		options[:output_folder] = output_folder;
	end

	opts.on('-h', '--help', 'Visualizza help') do
		puts opts
		exit
	end
end
parser.parse!
##

# Controlli dei parametri

if options[:project_name] == nil
	puts "Scrivi il nome del progetto che vuoi creare:"
	options[:project_name] = STDIN.gets.chomp
end

if options[:project_name] == nil
	abort();
end

if options[:project_name] == ""
	abort "Il nome del progetto non puo essere vuoto, riprova"
end

# Tolgo gli eventuali spazi all'interno del nome
options[:project_name] = options[:project_name].gsub(" ","")


if options[:package_name] == nil
	puts "Scrivi il package name del progetto (es: '#$boilerplate_package_name'):"
	options[:package_name] = STDIN.gets.chomp
end

if options[:package_name] == nil
	abort();
end

# Controllo che siano 3 le parole separate da punti

package_name_parts_array = options[:package_name].split(".")

if package_name_parts_array.length != 3
	abort("Attenzione: il nome del package deve essere formato da 3 parole separate da '.'")
end

if options[:output_folder] == nil
	puts "Indica il percorso dove vuoi che il progetto venga spostato:"
	options[:output_folder] = STDIN.gets.chomp
end

if options[:output_folder] == nil || options[:output_folder] == ""
	abort("E' obbligatorio indicare il percorso di destinazione del progetto");
end

if !File.directory? options[:output_folder]
	abort "Il percoso di destinazione del progetto (" + options[:output_folder]+ ")non e' valido"
end

puts "Vuoi creare un progetto con nome '" + options[:project_name] + "' e package name '" + options[:package_name]+ "' a partire dal progetto boilerplate? (Y/n)"
$confirm = STDIN.gets.chomp
if $confirm == "Y"
	puts "Il progetto verra' creato e spostato in " + options[:output_folder]

	# Iniziamo le procedure che servono per adeguare il progetto boilerplate ad un nuovo progetto

	# 1. Copio l'intera cartella relativa al progetto Boilerplate in una nuova con il nome del progetto
	if File.directory?(options[:project_name])
		puts "Esiste gia' una cartella con il nome del progetto, la vuoi sovrascrivere? (Y/n)"
		$confirm = STDIN.gets.chomp
		if $confirm == "Y"
			FileUtils.rm_rf(options[:project_name])
		else
			abort("Alla prossima")
		end
	end
	puts "Cartella di progetto pronta"

	FileUtils.cp_r $boilerplate_project_name + "/.", options[:project_name], :verbose => true

	# 2. Elimino la cartella relativa al repo git ma lascio .gitignore
	FileUtils.rm_rf(options[:project_name] + "/.git")
	puts "Eliminati i riferimenti al repo git, mantenendo il file .gitignore"
	# 3. Elimino il file .vcs che indica la presenza di un repo git. Verrà ricreato una volta aperto il progetto quando sarà presente un repo inizializzato
	if File.exist? options[:project_name] + "/.idea/vcs.xml"
		FileUtils.rm options[:project_name] + "/.idea/vcs.xml"
		puts "Eliminati i riferimenti a vcs.xml"
	end
	# 4. Elimino la cartella .gradle. Verrà ricreata una volta buildato il progetto
	FileUtils.rm_rf(options[:project_name] + "/.gradle")
	puts "Eliminati i riferimenti a gradle"
	# Elimino il file TiknilBoilerplateMVVM.iml che non serve più
	iml = options[:project_name] + "/" + $boilerplate_project_name + ".iml"
	if File.exist? iml
		FileUtils.rm iml
	end



	boilerplate_package_name_parts_array = $boilerplate_package_name.split(".")

	# 4. Cambio il nome delle cartelle "com" nel primo campo del package name dei moduli dell'app

	for $module in ["app", "core", "models", "services"]
		src_folder = options[:project_name] + "/" + $module + "/src"
		for $subfolder in ["/androidTest", "/main", "/test"]
			folder = src_folder + $subfolder + "/java"

			[0, 1, 2].each do |index|
				if boilerplate_package_name_parts_array[index] != package_name_parts_array[index]
					FileUtils.mv folder + "/" + boilerplate_package_name_parts_array[index], folder + "/" + package_name_parts_array[index]
				end
				folder = folder + "/" + package_name_parts_array[index]
			end
		end
	end

	puts "Aggiornate le cartelle (package) che contengono le classi kotlin"

	Find.find(options[:project_name]).each do |file_name|
		if (file_name != nil &&
			File.file?(file_name) &&
			!(file_name.include? "/generated") &&
			!(file_name.include? "/intermediates") &&
			!(file_name.include? "/outputs") &&
			!(file_name.include? "/tmp") &&
			!(file_name.include? "/gradle") &&
			!(file_name.include? "/keystore") &&
			!(file_name.include? "/libraries") &&
			((file_name.include? ".kt") ||
			(file_name.include? ".java") ||
			(file_name.include? ".xml") ||
			(file_name.include? ".gradle") ||
			(file_name.include? ".pro") ||
			(file_name.include? ".name") ||
			(file_name.include? ".iml") ||
			(file_name.include? ".md") ||
			(file_name.include? ".properties")))

			# 5. Se Trovo il file "Application" lo sostituisco con il nome del progetto + Application
			if file_name.include? $boilerplate_application_class
				FileUtils.mv file_name, (file_name.sub $boilerplate_application_class, options[:project_name] + "App.kt")
				#Aggiorno il nome del file per poterlo modificare
				file_name.sub! $boilerplate_application_class, options[:project_name] + "App.kt"
				puts "Aggiornata la classe Application"
			end

			# 6. Se Trovo il file "BoilerplateFonts" lo sostituisco con il nome del progetto + Fonts
			if file_name.include? $boilerplate_fonts_class
				FileUtils.mv file_name, (file_name.sub $boilerplate_fonts_class, options[:project_name] + "Fonts.kt")
				#Aggiorno il nome del file per poterlo modificare
				file_name.sub! $boilerplate_fonts_class, options[:project_name] + "Fonts.kt"
				puts "Aggiornata la classe Fonts"
			end

			# 7. Se Trovo il file "BoilerplateConstants" lo sostituisco con il nome del progetto + Constants
			if file_name.include? $boilerplate_constants_class
				FileUtils.mv file_name, (file_name.sub $boilerplate_constants_class, options[:project_name] + "Constants.kt")
				#Aggiorno il nome del file per poterlo modificare
				file_name.sub! $boilerplate_constants_class, options[:project_name] + "Constants.kt"
				puts "Aggiornata la classe Constants"
			end

			# 8. Se Trovo il file "BoilerplateApi" lo sostituisco con il nome del progetto + Api
			if file_name.include? $boilerplate_api_class
				FileUtils.mv file_name, (file_name.sub $boilerplate_api_class, options[:project_name] + "Api.kt")
				#Aggiorno il nome del file per poterlo modificare
				file_name.sub! $boilerplate_api_class, options[:project_name] + "Api.kt"
				puts "Aggiornata la classe Api"
			end

			# 9. Sostituisco ovunque trovo il package name di default con quello nuovo
			file_content = File.read(file_name)
			if ! file_content.valid_encoding?
			  	s = file_content.encode("UTF-16be", :invalid=>:replace, :replace=>"?").encode('UTF-8')
			else
				s = file_content
			end

			s = s.gsub($boilerplate_package_name,options[:package_name])
			s = s.gsub($boilerplate_project_name,options[:project_name])
			s = s.gsub($boilerplate_description,options[:project_name])
			s = s.gsub($boilerplate_description.downcase,options[:project_name].downcase)
			new_content = s

			File.open(file_name, "w") { |file| file.puts new_content }
		end
	end

	puts "Sostituito ovunque il package name con il nuovo #{options[:package_name]}"

	Dir.chdir(options[:project_name].to_s)	do
		puts system "gradle clean"

		puts "Eseguito gradlew clean"

		puts system "git init"

		puts system "git add *"

		puts system "git commit -m \"Initial commit\""
	end

	output_path = options[:output_folder].to_s
	if ! output_path.end_with? "/"
		output_path = output_path + "/"
	end
	output_path = output_path + options[:project_name].to_s

	puts "Sposto il progetto in " + output_path

	FileUtils.mv options[:project_name].to_s , output_path

	puts "Ok, ora sei pronto per iniziare. Apri il progetto generato in AndroidStudio!"
else
	abort("Alla prossima!")
end
