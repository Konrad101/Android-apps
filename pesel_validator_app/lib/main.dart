import 'package:flutter/material.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    final validatorAppTitle = 'Personal PESEL validator';

    return MaterialApp(
      title: validatorAppTitle,
      theme: ThemeData(
        primarySwatch: Colors.grey,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: Scaffold(
        appBar: AppBar(
          title: Text(validatorAppTitle),
        ),
        body: Validator(),
      ),
    );
  }
}

class Validator extends StatefulWidget {
  @override
  _ValidatorState createState() => _ValidatorState();
}

class _ValidatorState extends State<Validator> {
  final _globalKey = GlobalKey<FormState>();
  final _errorMessage = 'PESEL number must have 11 characters';

  final _textColor = Colors.blue[800];
  final _fontSize = 25.0;

  final peselWages = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
  final validatorController = TextEditingController();
  bool showResult = false;
  String checksum = "";
  String gender = "";
  String birthDate = "";

  void _validatePersonPESEL() {
    setState(() {
      int month = int.parse(validatorController.text.substring(2, 4));
      String day = validatorController.text.substring(4, 6);
      birthDate = day + "." + _getYearWithMonth(month);

      if (int.parse(validatorController.text.substring(9, 10)) % 2 != 0) {
        gender = "Male";
      } else {
        gender = "Female";
      }

      int checksumInt = 0;
      for (int i = 0; i < peselWages.length; i++) {
        checksumInt += int.parse(validatorController.text[i]) * peselWages[i];
      }

      int lastDigit = int.parse(
          validatorController.text[validatorController.text.length - 1]);
      if (10 - (checksumInt % 10) == lastDigit) {
        checksum = "valid";
      } else {
        checksum = "invalid";
      }
      showResult = true;
    });
  }

  String _getYearWithMonth(int month) {
    String year;
    int subtractFromMonths;
    if (month < 20) {
      year = "19";
      subtractFromMonths = 0;
    } else if (month < 40) {
      year = "20";
      subtractFromMonths = 20;
    } else if (month < 60) {
      year = "21";
      subtractFromMonths = 40;
    } else if (month < 80) {
      year = "22";
      subtractFromMonths = 60;
    } else {
      year = "18";
      subtractFromMonths = 80;
    }

    year += validatorController.text.substring(0, 2);
    String monthStr = (month - subtractFromMonths).toString().padLeft(2, '0');

    return monthStr + "." + year;
  }

  @override
  void dispose() {
    validatorController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Form(
        key: _globalKey,
        child: Column(children: <Widget>[
          TextFormField(
              validator: (value) {
                if (value.length != 11) {
                  setState(() {
                    showResult = false;
                  });
                  return _errorMessage;
                }
                return null;
              },
              controller: validatorController),
          ElevatedButton(
            onPressed: () {
              if (_globalKey.currentState.validate()) {
                _validatePersonPESEL();
              }
            },
            child: Text('Submit', style: TextStyle(fontSize: 20)),
          ),
          Visibility(
            visible: showResult,
            child: Column(
              children: [
                Padding(
                  padding: new EdgeInsets.all(5),
                  child: Text('Birthday: ' + birthDate,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
                Padding(
                  padding: new EdgeInsets.all(5),
                  child: Text('Gender: ' + gender,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
                Padding(
                  padding: new EdgeInsets.all(5),
                  child: Text('Checksum: ' + checksum,
                      style: TextStyle(color: _textColor, fontSize: _fontSize)),
                ),
              ],
            ),
          ),
        ]));
  }
}
