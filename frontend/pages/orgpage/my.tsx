import { FC } from "react";
import Image from "next/image";
import { useState } from "react";
import { styled, createTheme, ThemeProvider } from "@mui/material/styles";
import {
  Container,
  Grid,
  Typography,
  Button,
  Box,
  CssBaseline,
  IconButton,
  Stack,
} from "@mui/material";
import OrgMypageSidebar from "../../components/OrgMypageSidebar";
import Link from "next/link";
import helpImage from "../../public/images/help.png";
import EditIcon from "@mui/icons-material/Edit";
import { makeStyles } from "@material-ui/core/styles";

import BusinessIcon from "@mui/icons-material/Business";
import CallIcon from "@mui/icons-material/Call";
import MailIcon from "@mui/icons-material/Mail";

const mdTheme = createTheme();

const useStyles = makeStyles((theme) => ({
  customHoverFocus: {
    "&:hover, &.Mui-focusVisible": { backgroundColor: "#FFBC39" },
  },
  customColor: {
    backgroundColor: "#F8DD8E",
    "&:hover, &.Mui-focusVisible": { backgroundColor: "#FFBC39" },
  },
}));

const UpdateButton = styled(Button)({
  backgroundColor: "#5B321E",
  color: "white",
  fontWeight: "bold",
  "&:hover": {
    backgroundColor: "#CDAD78",
    color: "white",
  },
  // width: "50px",
});

const UpdateButton2 = styled(Button)({
  backgroundColor: "#CDAD78",
  color: "white",
  fontWeight: "bold",
  "&:hover": {
    backgroundColor: "#5B321E",
    color: "white",
  },
  // width: "50px",
});

const OrgMypage: FC = () => {
  const classes = useStyles();
  const [open, setOpen] = useState(true);
  const toggleDrawer = () => {
    setOpen(!open);
  };

  return (
    <ThemeProvider theme={mdTheme}>
      <Box sx={{ display: "flex" }}>
        <CssBaseline />
        <OrgMypageSidebar />
        {/* <Drawer
          variant="permanent"
          open={open}
          sx={{ backgroundColor: "#F8DD8E" }}
        >
          <List component="nav" sx={{ backgroundColor: "#F8DD8E" }}>
            {mainListItems}
            <Divider sx={{ my: 1 }} />
            {secondaryListItems}
          </List>
        </Drawer> */}
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            height: "100vh",
            overflow: "auto",
          }}
        >
          <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
            <Grid container spacing={1}>
              <Grid item xs={3}>
                <Image
                  src={helpImage}
                  alt="orgImage"
                  width="300px"
                  height="300px"
                />
              </Grid>
              <Grid item xs={8}>
                <Typography sx={{ mt: 2 }} variant="h4" fontWeight="bold">
                  수원시광교노인복지관
                </Typography>
                <Grid
                  sx={{ mt: 2 }}
                  container
                  direction="row"
                  alignItems="center"
                >
                  <BusinessIcon sx={{ mr: 2 }} />
                  <Typography align="center">
                    경기도 수원시 팔달구 중부대로 222번길 22 2-22
                  </Typography>
                </Grid>
                <Grid
                  sx={{ mt: 2 }}
                  container
                  direction="row"
                  alignItems="center"
                >
                  <CallIcon sx={{ mr: 2 }} />
                  <Typography align="center">010-7777-7777</Typography>
                </Grid>
                <Grid
                  sx={{ mt: 2 }}
                  container
                  direction="row"
                  alignItems="center"
                >
                  <MailIcon sx={{ mr: 2 }} />
                  <Typography align="center">test@gmail.com</Typography>
                </Grid>
                <Typography sx={{ mt: 2 }}>
                  기관 소개 : 아무래도 다시 돌아갈 순 없어 아무런 표정도 없이
                  이런 말하는 그런 내가 잔인한가요 제발 내 마음 설레이게 자꾸만
                  바라보게 하지 말아요 아무 일 없던 것처럼 그냥 스쳐지나갈
                  미련인 걸 알아요 아무리 사랑한다 말했어도 다시 돌아올 수 없는
                  그 때 그 맘이 부른다고 다시 오나요 아무래도 다시 돌아갈 순
                  없어 아무런 표정도 없이 이런 말하는 그런 내가 잔인한가요
                </Typography>
              </Grid>
              <Grid item xs={1}>
                <UpdateButton variant="contained" sx={{ mb: 15 }}>
                  수정
                </UpdateButton>
                {/* <UpdateButton variant="contained" size="small">
                  <EditIcon />
                </UpdateButton> */}
                {/* <IconButton aria-label="edit" className={classes.customColor}>
                  <EditIcon />
                </IconButton> */}
              </Grid>
            </Grid>
          </Container>
        </Box>
      </Box>
    </ThemeProvider>
  );
};

export default OrgMypage;