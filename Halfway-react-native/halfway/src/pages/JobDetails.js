import React from 'react';
import { View, StyleSheet, Text, TextInput, Button, Linking } from 'react-native';

export default class JobDetails extends React.Component {
    static navigationOptions = {
        title: 'Job details',
        headerStyle: { marginTop: 24 }
    };
    constructor(props) {
        super(props);
        var param = this.props.navigation.state.params.rowData;
        console.log(param);
        this.state = {
            jobID: param.id,
            jobName: param.name,
            jobDescription: param.description,
            jobDate: param.date,
            jobEmployer: param.employer,
            feedback: "send feedback"
        };
    };

    render() {
        const {goBack, setParams} = this.props.navigation;
        const { navigate } = this.props.navigation;
        return (
            <View>
                <TextInput
                    underlineColorAndroid="transparent"
                    style={styles.textInput}
                    onChangeText={(jobName) => this.setState({ jobName })}
                    value={this.state.jobName}
                />
                <TextInput
                    underlineColorAndroid="transparent"
                    style={styles.textInput}
                    onChangeText={(jobDescription) => this.setState({ jobDescription })}
                    value={this.state.jobDescription}
                />
                <TextInput
                    underlineColorAndroid="transparent"
                    style={styles.textInput}
                    onChangeText={(jobDate) => this.setState({ jobDate })}
                    value={this.state.jobDate}
                />
                <TextInput
                    underlineColorAndroid="transparent"
                    style={styles.textInput}
                    onChangeText={(jobEmployer) => this.setState({ jobEmployer })}
                    value={this.state.jobEmployer}
                />
                <TextInput
                    underlineColorAndroid="transparent"
                    style={styles.textInput}
                    onChangeText={(feedback) => this.setState({ feedback })}
                    placeholder={this.state.feedback}
                />

                <Button color="green" title="Save" onPress={() => {
                    console.log(this.state.jobName);
                    setParams({
                        rowData: {
                        id: this.state.jobID,
                        name: this.state.jobName,
                        description: this.state.jobDescription,
                        date: this.state.jobDate,
                        employer: this.state.jobEmployer
                        }
                    });
                    console.log(this.props.navigation)
                    goBack();
                    }
                 } />

                <Button title="Send feedback" onPress={() => this.sendEmail()} />
            </View>
        );
    }

    sendEmail() {
        let emailURL = 'mailto:somethingemail@gmail.com&?subject=FeedbackHalfWay&body=' + this.state.feedback;
        console.log(emailURL);
        Linking.openURL(emailURL);
    }
}

const styles = StyleSheet.create({
    textInput: {
        height: 50,
        borderRadius: 10,
        fontSize: 20,
        backgroundColor: "#01579B",
        color: "#fff",
        padding: 10,
        margin: 20
    },
    mybutton: {
        margin: 50,
        borderRadius: 10
    }
});